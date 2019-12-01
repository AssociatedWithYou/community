package com.jiane.service;

import com.jiane.dto.QuestionDTO;
import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;



    public List<QuestionDTO> getQuestions(Integer start, Integer end, User user) {
        List<Question> questions = null;
        if (user == null||user.getId()==null||user.getId()<1) {
            questions= questionMapper.findQuestionByPage(start,end);
        }else{
            questions= questionMapper.findQuestionByUser(start,end,user.getId());
        }
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


    public QuestionDTO findQuestionById(Integer id) {
        Question question  = questionMapper.findQuestionById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        System.out.println(question);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;
    }

    public void addOrUpdateQuestions(HttpSession session, Question question) {
        User user = (User)session.getAttribute("user");
        Long date = System.currentTimeMillis();
        question.setGmtModified(date);
        if (question.getId() == null) {

            question.setCreator(user.getId())
                    .setGmtCreate(date);

            questionMapper.createQuestion(question);
            return;
        }
        Integer i = questionMapper.updateQuestion(question);
        if (i == 0) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

    }
}
