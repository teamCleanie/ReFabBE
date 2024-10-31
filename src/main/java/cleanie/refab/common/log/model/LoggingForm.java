package cleanie.refab.common.log.model;

import lombok.Getter;

@Getter
public class LoggingForm {

    private String apiUrl;

    private String apiMethod;

    private Long queryCounts = 0L;

    private Long queryTime = 0L;

    public void setApiInfo(final String apiUrl, final String apiMethod) {
        this.apiUrl = apiUrl;
        this.apiMethod = apiMethod;
    }

    public void queryExecuted(final Long queryTime) {
        queryCounts++;
        this.queryTime += queryTime;
    }

    @Override
    public String toString() {
        return String.format("API URL: %s | Method: %s | Query Count: %d | Query Time: %d ms",
                apiUrl, apiMethod, queryCounts, queryTime);
    }
}
