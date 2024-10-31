package cleanie.repatch.common.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private final String AWS_REGION;
    private final String AWS_ACCESS_KEY;
    private final String AWS_SECRET_KEY;

    public S3Config(
            @Value("${cloud.aws.region.static}") String AWS_REGION,
            @Value("${cloud.aws.credentials.access-key}") String AWS_ACCESS_KEY,
            @Value("${cloud.aws.credentials.secret-key}") String AWS_SECRET_KEY) {
        this.AWS_REGION = AWS_REGION;
        this.AWS_ACCESS_KEY = AWS_ACCESS_KEY;
        this.AWS_SECRET_KEY = AWS_SECRET_KEY;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(AWS_REGION)
                .build();
    }
}
