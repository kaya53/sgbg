package com.sgbg.api.controller;

import com.sgbg.api.request.CommentReq;
import com.sgbg.api.response.BaseResponseBody;
import com.sgbg.api.response.CommentRes;
import com.sgbg.common.util.CookieUtil;
import com.sgbg.service.interfaces.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Comment API", description = "코멘트 생성, 방별 코멘트 목록 조회, 코멘트 수정, 코멘트 삭제 기능 제공")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private CookieUtil cookieUtil;


    @Operation(summary = "코멘트 생성 메서드")
    @PostMapping("/create")
    public  ResponseEntity createComment(@RequestBody CommentReq commentReq, HttpServletRequest request) throws Exception {

       Long userId = cookieUtil.getUserIdByToken(request);
        String result = commentService.createComment(commentReq, userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(2010, result));
    }


    @Operation(summary = "코멘트 수정 메서드")
    @PostMapping("/{commentId}/update")
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody @Valid String content) throws Exception {

        JSONObject parser =new JSONObject(content);
        String parsedContent = parser.getString("content");
        String result = commentService.updateComment(commentId, parsedContent);
        return ResponseEntity.status(200).body(BaseResponseBody.of(2000, result));
    }


    @Operation(summary = "코멘트 삭제 메서드")
    @PostMapping("/{commentId}/delete")
    public ResponseEntity deleteComment(@PathVariable Long commentId) throws Exception {
        String result = commentService.deleteComment(commentId);

        if(result.equals("Success")){
            return ResponseEntity.status(200).body(BaseResponseBody.of(2000, result));
        }
        return ResponseEntity.status(404).body(BaseResponseBody.of(4040, result));
    }

    @Operation(summary = "코멘트 목록 조회 메서드")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "코멘트 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = CommentRes.class)))
    )
    @GetMapping("/{roomId}")
    public ResponseEntity detailComment(@PathVariable Long roomId) throws Exception {

        List<CommentRes> commentRes = commentService.detailComment(roomId);
      
        return ResponseEntity.status(200).body(commentRes);
    }


}
