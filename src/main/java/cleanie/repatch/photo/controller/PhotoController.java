package cleanie.repatch.photo.controller;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.model.PhotoResponse;
import cleanie.repatch.photo.model.PhotoUploadRequest;
import cleanie.repatch.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    // 사진 저장
    @PostMapping
    public ResponseEntity<List<PhotoResponse>> uploadPhotos(
            @RequestBody PhotoUploadRequest request) throws IOException {
        List<PhotoResponse> responses = photoService.uploadAndSavePhotos(request);
        return ResponseEntity.ok(responses);
    }

    // 사진 삭제(1개씩)
    @DeleteMapping("/{photo_id}")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable(name = "photo_id") Long photoId){
        if (photoService.deletePhotoIfExistsById(photoId)){
            return ResponseEntity.ok(null);
        } else {
            throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
        }
    }
}
