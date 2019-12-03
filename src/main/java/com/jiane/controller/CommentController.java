package com.jiane.controller;

import com.jiane.dto.CommentDTO;
import com.jiane.dto.ResultDTO;
import com.jiane.enums.CommentTypeEnum;
import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.model.Comment;
import com.jiane.model.User;
import com.jiane.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO, HttpSession session){
        System.out.println(commentDTO);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_OFFLINE);
        }
        if (commentDTO.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_COMMENT_PARAM_NOT_EXISTS);
        }
        if (commentDTO.getType()==null|| !CommentTypeEnum.isExists(commentDTO.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (commentDTO.getContent().isEmpty() || commentDTO.getContent() == null) {
            throw new CustomizeException(CustomizeErrorCode.CONTENT_IS_NULL);
        }


        Comment comment = new Comment();
        Long now = System.currentTimeMillis();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setLikeCount(0L)
                .setGmtCreate(now)
                .setGmtModified(now)
                .setCommentator(user.getId());

        commentService.replyQuestionOrComment(comment);
        System.out.println("add comment ok");

        return ResultDTO.successOf(CustomizeErrorCode.COMMENT_RELEASE_SUCCESS);
    }
}
