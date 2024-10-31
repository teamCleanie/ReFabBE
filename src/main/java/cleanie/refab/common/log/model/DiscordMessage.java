package cleanie.refab.common.log.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DiscordMessage {

    private final String content;
    private final List<Embed> embeds;

    @Builder
    @Getter
    public static class Embed {

        private final String title;
        private final String description;
    }
}
