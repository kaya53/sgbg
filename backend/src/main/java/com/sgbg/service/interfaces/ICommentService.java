package com.sgbg.service.interfaces;

import com.sgbg.api.request.CommentReq;
import com.sgbg.api.response.CommentRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICommentService {

String createComment(CommentReq commentReq, Long userId) throws Exception;

String updateComment(Long commentId, String content) throws Exception;
String deleteComment(Long commentId) throws Exception;

List<CommentRes> detailComment(Long roomId) throws Exception;
}
