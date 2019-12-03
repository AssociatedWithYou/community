package com.jiane.controller;

import com.jiane.dto.CommentListDTO;
import com.jiane.dto.QuestionDTO;
import com.jiane.enums.CommentTypeEnum;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import com.jiane.service.CommentService;
import com.jiane.service.QuestionService;
import com.jiane.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/questions/{id}")
    public String gotoQuestion(@PathVariable("id") Integer id, Model model) {
        System.out.println(id);
        questionService.updateViewCount();
        QuestionDTO questionDto = questionService.findQuestionById(id);
        User userById = userMapper.findUserById(questionDto.getCreator());
        questionDto.setUser(userById);
        model.addAttribute("question", questionDto);

        List<CommentListDTO> commentListDTOS = commentService.findAllParentCommentsByQuestionIdAndType(questionDto.getId(), CommentTypeEnum.QUESTION.getType());

        Set<Integer> set = new HashSet<>();

        for (CommentListDTO commentListDTO : commentListDTOS) {
            set.add(commentListDTO.getCommentator());
        }

        for (Integer integer : set) {
            System.out.println(integer);
        }

        for (Integer aInteger : set) {
            User user = userService.findUserByCommentator(aInteger);
            System.out.println("for:"+user);
            for (int i = 0; i < commentListDTOS.size(); i++) {
                if (commentListDTOS.get(i).getCommentator().toString().equals(user.getId().toString())) {
                    commentListDTOS.get(i).setUser(user);
                }
            }
        }

        for (int i = 0; i < commentListDTOS.size(); i++) {
            System.out.println(commentListDTOS.get(i));
        }
        model.addAttribute("comments", commentListDTOS);
        return "question";
    }



}
