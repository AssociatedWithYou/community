package com.jiane.service;

import com.jiane.dto.QuestionDTO;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;




    public List<QuestionDTO> getQuestions(Integer start , Integer end){
        List<Question> questions= questionMapper.findQuestionByPage(start,end);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findUserById(question.getCreator());
            /*
            * 使用spring自带的BeanUtils工具类 快速便捷的进行两个类之间属性值的快速拷贝
            * */
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    public List<QuestionDTO> getQuestions(Integer start, Integer end, User user) {
        List<Question> questions= questionMapper.findQuestionByUser(start,end,user.getId());
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user1 = userMapper.findUserById(question.getCreator());
            /*
             * 使用spring自带的BeanUtils工具类 快速便捷的进行两个类之间属性值的快速拷贝
             * */
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user1);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
