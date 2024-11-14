package cleanie.repatch.post.controller;

import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostResponse;
import cleanie.repatch.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 개별 조회
    @Operation(summary = "게시글 개별 조회", description = "게시글 id를 받아 일치하는 게시글을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> readSinglePost(
            @PathVariable(name = "postId") Long postId){
        PostResponse response = postService.viewPost(postId);

        return ResponseEntity.ok(response);
    }

    // 게시글 발행
    @Operation(summary = "게시글 업로드", description = "게시글을 저장하고, 게시글 id를 반환합니다. (빈 필드 존재 시 저장 불가)")
    @PostMapping("/upload")
    public ResponseEntity<PostIdResponse> savePost(
            @RequestBody @Valid PostRequest request,
            @RequestParam(required = false, name = "draftId") Long draftId){
        PostIdResponse response = (draftId == null) ?
                postService.savePost(request) :
                postService.savePostFromDraft(draftId);

        return ResponseEntity.ok(response);
    }

    // 발행된 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글을 수정하고, 게시글 id를 반환합니다.")
    @PostMapping("/edit/{postId}")
    public ResponseEntity<PostIdResponse> editPost(
            @RequestBody @Valid PostRequest request,
            @PathVariable(name = "postId") Long postId){
        PostIdResponse response = postService.updatePost(request, postId);

        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Long postId){
        if (postService.deletePostById(postId)) {
            return ResponseEntity.ok("deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("delete failed");
        }
    }
}
