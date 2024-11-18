package cleanie.repatch.photo.domain;

import cleanie.repatch.photo.model.response.PostPhotoResponse;
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
    private List<PostPhoto> postPhotoList;

    public List<PostPhoto> getPostPhotoList() {
        if (postPhotoList == null) {
            postPhotoList = new ArrayList<>();
        }
        return postPhotoList;
    }

    public void addPhoto(PostPhoto postPhoto) {
        getPostPhotoList().add(postPhoto);
    }

    public void addPhotos(List<PostPhoto> postPhotos) {
        getPostPhotoList().addAll(postPhotos);
    }

    public static List<PostPhotoResponse> toPhotoResponses(DraftPhotos draftPhotos) {
        return draftPhotos.getPostPhotoList().stream()
                .map(photo -> new PostPhotoResponse(photo.getId(), photo.getImageUrl()))
                .toList();
    }
}

