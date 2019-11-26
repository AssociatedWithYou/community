package com.jiane.controller;


import com.jiane.dto.QuestionDTO;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import com.jiane.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String getProfile( @PathVariable(name="action") String action , Model model,
                                      HttpServletRequest request){
        System.out.println("aaa");
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length==0){
            return "index";
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                System.out.println(user);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        if ("questions".equals(action)) {
            model.addAttribute("titleBar", "我的问题");
        }
        return "profile";
    }

    @GetMapping("/profile/user/getQuestions")
    @ResponseBody
    public Map<String,Object> getQuestionsByUser(HttpServletRequest request,Integer currentPage, Integer record, HttpSession session){

        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length==0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                System.out.println(user);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        User user = (User)session.getAttribute("user");

        Integer totalCounts = questionMapper.findCountsByUser(user);//总数据条数

        Integer totalPages = totalCounts % record == 0 ? totalCounts / record : totalCounts / record + 1;//总页数
        if (totalPages == 0) {
            totalPages = 1;
        }
        Integer myCurrentPage = 1;
        if (currentPage>0&&currentPage<=totalPages){
            myCurrentPage = currentPage;
        }

        if(currentPage>totalPages){
            myCurrentPage = totalPages;
        }

        Integer start = (myCurrentPage - 1) * record;
        Integer end = record;
        List<QuestionDTO> questions = questionService.getQuestions(start,end,user);

        Map<String, Object> map = new HashMap<>();

        map.put("totalPage", totalPages);
        map.put("currentPage", myCurrentPage);//当前页
        map.put("questions", questions);//当前页的数据
        map.put("record", record);//每页条数
        List<Integer> pages = new ArrayList<>();
        if (totalPages<5){
            for (int i = 1 ; i <=totalPages; i++){
                pages.add(i);
            }
        }else{
            if(myCurrentPage<4){
                pages.add(1);
                pages.add(2);
                pages.add(3);
                pages.add(4);
                pages.add(5);
            } else if (myCurrentPage>=totalPages-2) {
                pages.add(totalPages-4);
                pages.add(totalPages-3);
                pages.add(totalPages-2);
                pages.add(totalPages-1);
                pages.add(totalPages);
            }else{
                pages.add(myCurrentPage-2);
                pages.add(myCurrentPage-1);
                pages.add(myCurrentPage);
                pages.add(myCurrentPage+1);
                pages.add(myCurrentPage+2);
            }
        }
        map.put("pagetoolbar", pages);
        return map;
    }
}
