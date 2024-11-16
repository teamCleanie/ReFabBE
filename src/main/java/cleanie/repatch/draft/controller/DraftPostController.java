package cleanie.repatch.draft.controller;

import cleanie.repatch.draft.model.request.DraftPostRequest;
import cleanie.repatch.draft.model.response.DraftPostResponse;
import cleanie.repatch.draft.model.response.DraftPostIdResponse;
import cleanie.repatch.draft.service.DraftPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/draft")
@RequiredArgsConstructor
public class DraftPostController {

    private final DraftPostService draftPostService;

    @Operation(summary = "임시저장 게시글 개별 조회", description = "임시저장글 id를 받아 일치하는 임시저장글을 조회합니다.")
    @GetMapping("/{draftId}")
    public ResponseEntity<DraftPostResponse> readSingleDraftPost(
            @PathVariable(name = "draftId") Long draftId){
        DraftPostResponse response = draftPostService.viewDraftPost(draftId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 임시저장", description = "게시글을 임시 저장하고, 임시저장글 id를 반환합니다. (빈 필드 저장 가능)")
    @PostMapping
    public ResponseEntity<DraftPostIdResponse> saveDraftPost(
            @RequestBody DraftPostRequest request,
            @Parameter(description = "임시저장글 id", example = "1") @RequestParam(required = false, name = "draftId") Long draftId){
        DraftPostIdResponse response = (draftId == null) ?
                draftPostService.saveDraftPost(request) :
                draftPostService.updateDraftPost(request, draftId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "임시저장글 삭제", description = "임시저장된 게시글을 삭제합니다.")
    @DeleteMapping("/delete/{draftId}")
    public ResponseEntity<?> deleteDraftPost(@PathVariable(name = "draftId") Long draftId) {
        draftPostService.deleteDraftPostById(draftId);

        return ResponseEntity.ok("deleted");
    }
}
