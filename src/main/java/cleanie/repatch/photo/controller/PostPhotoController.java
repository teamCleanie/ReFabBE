package cleanie.repatch.photo.controller;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.model.response.PostPhotoResponse;
import cleanie.repatch.photo.model.request.PostPhotoUploadRequest;
import cleanie.repatch.photo.service.PostPhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PostPhotoController {

    private final PostPhotoService postPhotoService;

    @Operation(summary = "사진 업로드", description = "사진을 s3에 업로드하고, 사진 id와 url을 반환합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostPhotoResponse>> uploadPhotos(
            @ModelAttribute PostPhotoUploadRequest request) throws IOException {
        List<PostPhotoResponse> responses = postPhotoService.uploadAndSavePhotos(request);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "사진 삭제", description = "사진(1장)을 삭제합니다.")
    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable(name = "photoId") Long photoId){
        if (postPhotoService.deletePhotoIfExistsById(photoId)){
            return ResponseEntity.ok(null);
        } else {
            throw new BadRequestException(ExceptionCode.FILE_NOT_FOUND);
        }
    }
}
