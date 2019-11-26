package com.jiane.controller;

import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @PostMapping("/publish")
    @ResponseBody
    public String addQuestion(Question question, HttpServletRequest request, Model model){
        System.out.println("-------------------------");
        System.out.println(question);

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
                    request.getSession().setAttribute("user", user);
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

        Long date = System.currentTimeMillis();
        question.setCreator(user.getId())
                .setGmtCreate(date)
                .setGmtModified(date);
        questionMapper.createQuestion(question);
        System.out.println("发布成功");
        return "{\"msg\":\"登录成功,等待跳转...\"}";
    }
}
