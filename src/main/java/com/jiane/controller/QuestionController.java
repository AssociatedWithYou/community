package com.jiane.controller;

import com.jiane.dto.QuestionDTO;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import com.jiane.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/questions/{id}")
    public String gotoQuestion(@PathVariable("id") Integer id, Model model) {
        System.out.println(id);
        questionService.updateViewCount();
        QuestionDTO questionDto = questionService.findQuestionById(id);
        User userById = userMapper.findUserById(questionDto.getCreator());
        questionDto.setUser(userById);
        model.addAttribute("question", questionDto);
        return "question";
    }



}
