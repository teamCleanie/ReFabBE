package cleanie.repatch.photo.domain;

import cleanie.repatch.photo.model.response.PhotoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DraftPhotos {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "draftId")
    private List<Photo> photoList;

    public List<Photo> getPhotoList() {
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        return photoList;
    }

    public void addPhoto(Photo photo) {
        getPhotoList().add(photo);
    }

    public void addPhotos(List<Photo> photos) {
        getPhotoList().addAll(photos);
    }

    public static List<PhotoResponse> toPhotoResponses(DraftPhotos draftPhotos) {
        return draftPhotos.getPhotoList().stream()
                .map(photo -> new PhotoResponse(photo.getId(), photo.getImageUrl()))
                .toList();
    }
}

