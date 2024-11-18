package cleanie.repatch.common.s3.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsS3Properties {

    private final Region region;
    private final String bucket;
    private final Credentials credentials;
    private final String cloudfront;

    public AwsS3Properties(Region region, String bucket, Credentials credentials, String cloudfront){
        this.region = region;
        this.bucket = bucket;
        this.credentials = credentials;
        this.cloudfront = cloudfront;
    }

    public String getAccessKey() {
        return credentials.getAccessKey();
    }

    public String getSecretKey() {
        return credentials.getSecretKey();
    }

    public String getRegion() {
        return region.getStaticRegion();
    }

    @Getter
    public static class Credentials {
        private final String accessKey;
        private final String secretKey;

        public Credentials(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }
    }

    @Getter
    public static class Region {
        private final String staticRegion;

        public Region(String staticRegion) {
            this.staticRegion = staticRegion;
        }
    }
}
