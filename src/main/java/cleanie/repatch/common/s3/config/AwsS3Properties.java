package cleanie.repatch.common.s3.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "cloud.aws")
public class AwsS3Properties {

    private final Region region;
    private final String bucket;
    private final Credentials credentials;

    public AwsS3Properties(Region region, String bucket, Credentials credentials){
        this.region = region;
        this.bucket = bucket;
        this.credentials = credentials;
    }

    @PostConstruct
    public void checkAwsS3Properties() {
        System.out.println("Region: " + getRegion());
        System.out.println("Credentials: "+ (credentials.accessKey!=null));
        System.out.println("Bucket: " + bucket);
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
