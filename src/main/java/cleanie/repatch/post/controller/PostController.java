package cleanie.repatch.post.controller;

import cleanie.repatch.post.model.PostRequest;
import cleanie.repatch.post.model.PostResponse;
import cleanie.repatch.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 개별 조회
    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponse> readSinglePost(
            @PathVariable(name = "post_id") Long postId){
        PostResponse response = postService.viewSinglePost(postId);

        return ResponseEntity.ok(response);
    }

    // 페이지네이션 적용한 판매/구매 게시글 목록 조회
//    @GetMapping()
//    public ResponseEntity<PostResponse> readPosts(
//            @RequestParam(name = "post_type") String postType){
//
//    }

    // 게시글 임시저장
    @PostMapping("/draft/{post_id}")
    public ResponseEntity<PostResponse> saveDraft(
            @RequestBody PostRequest request,
            @PathVariable(required = false, name = "post_id") Long postId){
        PostResponse response = (postId == null) ?
                postService.savePost(request, false) :
                postService.updatePost(request, postId, false);

        return ResponseEntity.ok(response);
    }

    // 게시글 발행
    @PostMapping("/upload/{post_id}")
    public ResponseEntity<PostResponse> savePost(
            @RequestBody PostRequest request,
            @PathVariable(required = false, name = "post_id") Long postId){
        PostResponse response = (postId == null) ?
                postService.savePost(request, true) :
                postService.updatePost(request, postId, true);

        return ResponseEntity.ok(response);
    }

    // 발행된 게시글 수정
    @PostMapping("/edit/{post_id}")
    public ResponseEntity<PostResponse> editPost(
            @RequestBody PostRequest request,
            @PathVariable(name = "post_id") Long postId){
        PostResponse response = postService.updatePost(request, postId, true);

        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
//    @DeleteMapping("/delete/{post_id}")
//    public ResponseEntity<> deletePost(@PathVariable(name = "post_id") Long postId){
//
//    }
}
