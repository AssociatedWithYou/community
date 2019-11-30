package com.jiane.controller;


import com.jiane.dto.QuestionDTO;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import com.jiane.service.QuestionService;
import com.jiane.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    PagingUtil pagingUtil;

    @GetMapping("/profile/{action}")
    public String getProfile( @PathVariable(name="action") String action , Model model){
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

        User user = (User)session.getAttribute("user");

        if (user == null) {
            return null;
        }

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
        List<Integer> pages = pagingUtil.getPageList(myCurrentPage,totalPages);
        map.put("pagetoolbar", pages);
        System.out.println("profile:");
        for (Integer page : pages) {
            System.out.println(page);
        }
        return map;
    }
}

