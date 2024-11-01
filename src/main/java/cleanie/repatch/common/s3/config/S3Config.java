package cleanie.repatch.common.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsS3Properties.class)
public class S3Config {

    private final AwsS3Properties awsS3Properties;

    public S3Config(AwsS3Properties awsS3Properties){
        this.awsS3Properties = awsS3Properties;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                awsS3Properties.getAccessKey(),
                awsS3Properties.getSecretKey());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsS3Properties.getRegion())
                .build();
    }
}
