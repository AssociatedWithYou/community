package com.jiane.controller;

import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.Tag;
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
    public String publish(Model model){
        model.addAttribute("tags",Tag.getTags());
        return "publish";
    }


    //跳转到更显问题界面
    @GetMapping("/publish/{id}")
    public String goToPublish(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Question questionById = questionMapper.findQuestionById(id);
        if (questionById == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        model.addAttribute("question", questionById);
        model.addAttribute("tags",Tag.getTags());
        return "publish";
    }


    //发布/更新问题
    @PostMapping("/publish")
    @ResponseBody
    public String addQuestion(Question question, HttpServletRequest request, Model model){
        if (question.getTitle()==null||question.getTitle().isEmpty()){
            return "{\"success\": 0,\"msg\": \"标题不能为空\"}";
        }
        if (question.getTitle().length()>50){
            return "{\"success\": 0,\"msg\": \"标题过长,请少于50个字符\"}";
        }
        if (question.getDescription()==null||question.getDescription().isEmpty()){
            return "{\"success\": 0,\"msg\": \"问题描述不能为空\"}";
        }
        if (question.getTags() == null || question.getTags().isEmpty()) {
            return "{\"success\": 0,\"msg\": \"标签不能为空\"}";
        }
        System.out.println(question.getTags());
        if (question.getTags().contains("c  ")){
            String c = question.getTags().replace("c  ", "c++");
            question.setTags(c);
        }

        String[] split = question.getTags().split(",");

        String illegalTags = Tag.getIllegalTags(split);
        if (illegalTags!=null){
            return "{\"success\": 0,\"msg\": \"存在非法标签:"+illegalTags+"\"}";
        }

        System.out.println("-------------------------");
        System.out.println("question-publish:"+question);
        HttpSession session = request.getSession();
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            System.out.println("cookie为空");
            return "{\"success\": 0,\"msg\": \"请先登陆\"}";
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
            return "{\"success\": 0,\"msg\": \"请先登陆\"}";
        }

        System.out.println("执行了发布或者更新");
        questionService.addOrUpdateQuestions(session,question);

        System.out.println("发布成功");
        model.addAttribute("tags",Tag.getTags());
        return "{\"success\": 1,\"msg\": \"发布成功,等待跳转...\"}";
    }
}
