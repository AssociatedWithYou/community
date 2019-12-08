package com.jiane.service;

import com.jiane.dto.CommentListDTO;
import com.jiane.enums.CommentTypeEnum;
import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.mapper.CommentMapper;
import com.jiane.mapper.QuestionMapper;
import com.jiane.model.Comment;
import com.jiane.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionService questionService;


    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    public void replyQuestionOrComment(Comment comment) {
        if (comment.getType()== CommentTypeEnum.COMMENT.getType()){
            //回复别人的评论
            Comment dbComment =  commentMapper.selectCommentByParentId(comment.getParentId(),CommentTypeEnum.QUESTION.getType());
            if (dbComment == null) {
                System.out.println("==null");
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            String s = dbComment.getId().toString();
            Integer i = Integer.parseInt(s);
            commentMapper.updateCommentCount(i);
            commentMapper.addComment(comment);
        }else if (comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //回复问题
            String s = comment.getParentId().toString();
            System.out.println("s:"+s);
            Integer i = Integer.parseInt(s);
            System.out.println(i);
            Question question = questionService.selectQuestionById(i);
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.addComment(comment);
            questionService.updateCommentCount(question);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<CommentListDTO> findAllParentCommentsByQuestionIdAndType(Integer id, Integer question) {
        return commentMapper.findAllParentCommentsByQuestionIdAndType(id, question);
    }
}
