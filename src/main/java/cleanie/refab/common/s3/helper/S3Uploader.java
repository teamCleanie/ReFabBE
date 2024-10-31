package cleanie.refab.common.s3.helper;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Component
public class S3Uploader {

    private final String BUCKET_NAME;
    private final AmazonS3 amazonS3Client;

    public S3Uploader(
            @Value("${cloud.aws.s3.bucket}") String BUCKET_NAME,
            AmazonS3 amazonS3) {
        this.BUCKET_NAME = BUCKET_NAME;
        this.amazonS3Client = amazonS3;
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        amazonS3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getFileUrl(fileName);
    }

    public String getFileUrl(String fileName){
        URL url = amazonS3Client.getUrl(BUCKET_NAME, fileName);
        return (url != null) ? url.toString() : "URL not available";
    }

    public void deleteFile(String fileName){
        amazonS3Client.deleteObject(BUCKET_NAME, fileName);
    }
}
