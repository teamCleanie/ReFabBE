package cleanie.refab.common.log.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggingForm {

    private final String apiUrl;

    private final String apiMethod;

    private Long queryCounts = 0L;

    private Long queryTime = 0L;

    public void queryExecuted(final Long queryTime) {
        queryCounts++;
        this.queryTime += queryTime;
    }
}
