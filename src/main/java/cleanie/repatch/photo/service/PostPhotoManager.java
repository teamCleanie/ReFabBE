package cleanie.repatch.photo.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostPhotoManager {

    private final PhotoRepository photoRepository;

    public List<PhotoEntity> getPhotoEntitiesFromIds(List<Long> photoIds) {

        return photoIds.stream()
                .map(id -> photoRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_REQUEST)))
                .collect(Collectors.toList());
    }

    public void addPostIdToPhotoEntities(List<PhotoEntity> photoEntities, Long postId) {
        photoEntities.forEach(photo -> addPostIdToPhotoEntity(photo, postId));
    }

    public PhotoEntity addPostIdToPhotoEntity(PhotoEntity photoEntity, Long postId){
        PhotoEntity updatedPhoto = photoEntity.toBuilder()
                .postId(postId)
                .build();

        return photoRepository.save(updatedPhoto);
    }

    public List<String> getImageUrlFromEntities(List<PhotoEntity> photoEntities){

        return photoEntities.stream()
                .map(PhotoEntity::getImageUrl)
                .collect(Collectors.toList());
    }
}
