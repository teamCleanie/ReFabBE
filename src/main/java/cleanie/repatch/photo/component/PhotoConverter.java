package cleanie.repatch.photo.component;

import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.model.response.PhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class PhotoConverter {

    public Photo toPhotoEntity(String imageUrl){
        return Photo.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public PhotoResponse toPhotoResponse(Photo photo){
        return PhotoResponse.builder()
                .id(photo.getId())
                .imageUrl(photo.getImageUrl())
                .build();
    }
}
