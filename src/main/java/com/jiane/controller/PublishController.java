package com.jiane.controller;

import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.User;
import com.jiane.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    //跳转到更显问题界面
    @GetMapping("/publish/{id}")
    public String goToPublish(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Question questionById = questionMapper.findQuestionById(id);
        model.addAttribute("question", questionById);
        return "publish";
    }


    //发布/更新问题
    @PostMapping("/publish")
    @ResponseBody
    public String addQuestion(Question question, HttpServletRequest request, Model model){
        System.out.println("-------------------------");
        System.out.println("question-publish:"+question);
        HttpSession session = request.getSession();
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            System.out.println("cookie为空");
            return "{\"msg\":\"请先登录\"}";
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                System.out.println(user);
                if (user != null) {
                    session.setAttribute("user", user);
                }
                break;
            }
        }

        if (user == null) {
           /* model.addAttribute("error", "请先登录");
            return "publish";*/
            System.out.println("先去登录");
            return "{\"msg\":\"请先登录\"}";
        }

        System.out.println("执行了发布或者更新");
        questionService.addOrUpdateQuestions(session,question);

        System.out.println("发布成功");
        return "{\"msg\":\"登录成功,等待跳转...\"}";
    }
}
