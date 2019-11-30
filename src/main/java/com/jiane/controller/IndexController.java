package com.jiane.controller;

import com.jiane.dto.QuestionDTO;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.service.QuestionService;
import com.jiane.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    PagingUtil pagingUtil;

    @GetMapping("/")
    public String gotoIndex(HttpServletRequest request){
        return "index";
    }



    @GetMapping("/getQuestions")
    @ResponseBody
    public Map<String,Object> findQuestionByPage(Integer currentPage,Integer record){//当前页和每页条数

        Integer totalCounts = questionMapper.findCounts();//总数据条数
        Integer totalPages = totalCounts % record == 0 ? totalCounts / record : totalCounts / record + 1;//总页数
        Integer myCurrentPage = 1;
        if (currentPage>0&&currentPage<=totalPages){
            myCurrentPage = currentPage;
        }

        if(currentPage>totalPages){
            myCurrentPage = totalPages;
        }

        Integer start = (myCurrentPage - 1) * record;
        Integer end = record;
        List<QuestionDTO> questions = questionService.getQuestions(start,end,null);

        Map<String, Object> map = new HashMap<>();

        map.put("totalPage", totalPages);
        map.put("currentPage", myCurrentPage);//当前页
        map.put("questions", questions);//当前页的数据
        map.put("record", record);//每页条数
        List<Integer> pages = pagingUtil.getPageList(myCurrentPage,totalPages);
        map.put("pagetoolbar", pages);
        return map;
    }
}
