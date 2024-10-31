package cleanie.refab.common.log.alert;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import cleanie.refab.common.exception.DiscordWebhookException;
import cleanie.refab.common.log.model.DiscordMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cleanie.refab.common.exception.model.ExceptionCode.DISCORD_WEBHOOK_ERROR;
import static cleanie.refab.common.log.model.MdcPreference.*;

@Slf4j
public class DiscordAppender extends AppenderBase<ILoggingEvent> {

    private static final String WEBHOOK_TITLE = "# 🚨 서버 에러 발생 🔥";
    private static final String WEBHOOK_INFO_MESSAGE = "ℹ️ 에러 정보";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final int MAX_MESSAGE_LENGTH = 1500;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebClient webClient;

    @Setter
    private String webhookUrl;

    @Override
    public void start() {
        if (webhookUrl == null) {
            addError("No webhookUrl set for the appender named [" + name + "].");
            return;
        }
        webClient = WebClient.builder()
                .baseUrl(webhookUrl)
                .build();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        try {
            String payload = createPayload(event);
            sendDiscordMessage(payload)
                    .timeout(TIMEOUT)
                    .doOnError(e -> {
                        log.error("Failed to send Discord message", e);
                        throw new DiscordWebhookException(DISCORD_WEBHOOK_ERROR);
                    })
                    .onErrorResume(e -> Mono.empty())
                    .block();
        } catch (JsonProcessingException e) {
            log.error("Failed to create Discord message payload", e);
            throw new DiscordWebhookException(DISCORD_WEBHOOK_ERROR);
        }
    }

    private Mono<Void> sendDiscordMessage(String payload) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private String createPayload(ILoggingEvent event) throws JsonProcessingException {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();

        String errorMessage = getErrorMessage(event);
        String stackTrace = getStackTrace(event);

        DiscordMessage message = DiscordMessage.builder()
                .content(WEBHOOK_TITLE)
                .embeds(List.of(
                        DiscordMessage.Embed.builder()
                                .title(WEBHOOK_INFO_MESSAGE)
                                .description(
                                        "### 🕖 발생 시간\n" +
                                                Optional.ofNullable(mdcPropertyMap.get(REQUEST_TIME.name())).orElse("N/A") +
                                                "\n\n### 🔗 요청 URL\n" +
                                                Optional.ofNullable(mdcPropertyMap.get(REQUEST_URI.name())).orElse("N/A") +
                                                "\n\n### 👨‍💻 요청 ID\n" +
                                                Optional.ofNullable(mdcPropertyMap.get(REQUEST_ID.name())).orElse("N/A") +
                                                "\n\n### ❌ 에러 메시지\n" +
                                                errorMessage +
                                                "\n\n### 📄 Stack Trace\n```java\n" +
                                                stackTrace +
                                                "\n```"
                                )
                                .build()))
                .build();

        return objectMapper.writeValueAsString(message);
    }

    private String getErrorMessage(ILoggingEvent event) {
        return Optional.ofNullable(event.getThrowableProxy())
                .map(IThrowableProxy::getMessage)
                .orElse(event.getMessage());
    }

    private String getStackTrace(ILoggingEvent event) {
        StringBuilder stackTrace = new StringBuilder();
        IThrowableProxy throwableProxy = event.getThrowableProxy();

        if (throwableProxy != null) {
            stackTrace.append(throwableProxy.getClassName())
                    .append(": ")
                    .append(throwableProxy.getMessage())
                    .append("\n");

            for (StackTraceElementProxy element : throwableProxy.getStackTraceElementProxyArray()) {
                stackTrace.append("\tat ")
                        .append(element.toString())
                        .append("\n");

                if (stackTrace.length() > MAX_MESSAGE_LENGTH) {
                    stackTrace.append("... (more stack traces omitted)");
                    break;
                }
            }

            IThrowableProxy cause = throwableProxy.getCause();
            if (cause != null) {
                stackTrace.append("\nCaused by: ")
                        .append(cause.getClassName())
                        .append(": ")
                        .append(cause.getMessage());
            }
        } else {
            stackTrace.append(event.getFormattedMessage());
        }

        return stackTrace.toString();
    }
}
