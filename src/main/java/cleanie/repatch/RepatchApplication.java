package cleanie.repatch;

import cleanie.repatch.common.s3.config.AwsS3Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(AwsS3Properties.class)
@EnableJpaAuditing
public class RepatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepatchApplication.class, args);
    }

}
