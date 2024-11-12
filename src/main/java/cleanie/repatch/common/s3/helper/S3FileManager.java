package cleanie.repatch.common.s3.helper;

import cleanie.repatch.common.s3.config.AwsS3Properties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Component
public class S3FileManager {

    private final String BUCKET_NAME;
    private final String CLOUDFRONT_DOMAIN;
    private final AmazonS3 amazonS3Client;

    public S3FileManager(AwsS3Properties awsS3Properties, AmazonS3 amazonS3) {
        this.BUCKET_NAME = awsS3Properties.getBucket();
        this.CLOUDFRONT_DOMAIN = awsS3Properties.getCloudfront();
        this.amazonS3Client = amazonS3;
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = fileName + extension;
        amazonS3Client.putObject(new PutObjectRequest(BUCKET_NAME, newFileName, file.getInputStream(), null));
        return getCloudFrontUrl(newFileName);
    }

    public String getFileUrl(String fileName) {
        URL url = amazonS3Client.getUrl(BUCKET_NAME, fileName);
        return (url != null) ? url.toString() : "URL not available";
    }

    public String getCloudFrontUrl(String fileName) {
        return CLOUDFRONT_DOMAIN + "/" + fileName;
    }

    public void deleteFile(String fileName){
        amazonS3Client.deleteObject(BUCKET_NAME, fileName);
    }
}
