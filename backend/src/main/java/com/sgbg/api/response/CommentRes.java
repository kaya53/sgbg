package com.sgbg.api.response;

import com.sgbg.domain.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentRes extends BaseResponseBody {

    private String content;
    private Long commentId;
    private String createdAt;
    private String username;
    private Integer userScore;
    private Integer hostScore;
    private Long kakaoNumber;


//    public static CommentRes detailComment(Integer statusCode, String message, Long commentId, String content, String createdAt, String username, Integer userScore) {
//        CommentRes res = new CommentRes();
//        res.setStatusCode(statusCode);
//        res.setMessage(message);
//        res.setCommentId(commentId);
//        res.setContent(content);
//        res.setCreatedAt(createdAt);
//        res.setUsername(username);
//        res.setUserScore(userScore);
//        return res;
//    }

    public static CommentRes detailComment(Comment comment) {
        CommentRes res = new CommentRes();
        res.setStatusCode(2000);
        res.setMessage("게시글 조회 성공");
        res.setCommentId(comment.getCommentId());
        res.setContent(comment.getContent());
        res.setCreatedAt("작성시간");
        res.setUsername("이름");
        res.setUserScore(1);
        res.setHostScore(1);
        res.setKakaoNumber(1L);
        return res;
    }

}
