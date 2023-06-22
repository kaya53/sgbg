package com.sgbg.service;

import com.sgbg.api.request.CommentReq;
import com.sgbg.api.response.CommentRes;
import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.domain.Comment;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.RoomRepository;
import com.sgbg.repository.UserRepository;
import com.sgbg.repository.interfaces.CommentRepository;
import com.sgbg.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public String createComment(CommentReq commentReq, Long userId) throws Exception {

        Comment comment = new Comment();
        // 임의로 저장한 값 -> token 이용해서 useId 가져오기
        comment.setUserId(userId);
        Room room = roomRepository.findById(commentReq.getRoomId()).orElseThrow();
        comment.setRoom(room);
        comment.setContent(commentReq.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return "Accepted";
    }

    @Override
    public String updateComment(Long commentId, String content) throws Exception {
        Comment comment = commentRepository.findById(commentId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        comment.setContent(content);
        commentRepository.save(comment);

        return "Success";
    }

    @Override
    @Transactional
    public String deleteComment(Long commentId) throws Exception {

        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return "Success";
        } else {
            return "Fail";
        }
    }

    @Override
    public List<CommentRes> detailComment(Long roomId) throws Exception {
        List<Comment> commentList = commentRepository.findAllByRoom_idOrderByCreatedAtDesc(roomId);
        List<CommentRes> commentResList = new ArrayList<>();

        for (Comment comment : commentList) {

            CommentRes commentRes = CommentRes.detailComment(comment);

            User user = userRepository.findById(comment.getUserId()).orElse(null);

            if(user == null) {
                throw new NotFoundException("Writer Not Found");
            }

            commentRes.setUsername(user.getName());
            commentRes.setUserScore(user.getMemberScore());
            commentRes.setHostScore(user.getHostScore());

            Date createdDate = java.sql.Timestamp.valueOf(comment.getCreatedAt());
            long regTime = createdDate.getTime();
            long curTime = System.currentTimeMillis();
            long diffTime = (curTime - regTime) / 1000;

            String msg = "";

            if (diffTime < 60) {
                // sec
                msg = diffTime + "초 전";
            } else if ((diffTime /= 60) < 60) {
                // min
                msg = diffTime + "분 전";
            } else if ((diffTime /= 60) < 24) {
                // hour
                msg = (diffTime) + "시간 전";
            } else if ((diffTime /= 24) < 30) {
                // day
                msg = (diffTime) + "일 전";
            } else if ((diffTime /= 30) < 12) {
                // day
                msg = (diffTime) + "달 전";
            } else {
                msg = (diffTime) + "년 전";
            }
            commentRes.setCreatedAt(msg);
            commentRes.setKakaoNumber(user.getAuth().getKakaoNumber());

            commentResList.add(commentRes);
        }

        return commentResList;
    }
}



