package com.jiane.service;

import com.jiane.enums.CommentTypeEnum;
import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.mapper.CommentMapper;
import com.jiane.mapper.QuestionMapper;
import com.jiane.model.Comment;
import com.jiane.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Comment dbComment =  commentMapper.selectCommentByParentId(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
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
}
