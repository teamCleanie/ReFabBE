package cleanie.repatch.photo.component;

import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.model.response.PhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class PhotoConverter {

    public PhotoEntity toPhotoEntity(String imageUrl){
        return PhotoEntity.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public PhotoResponse toPhotoResponse(PhotoEntity photo){
        return PhotoResponse.builder()
                .id(photo.getId())
                .imageUrl(photo.getImageUrl())
                .build();
    }
}
