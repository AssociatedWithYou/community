package com.jiane.service;

import com.jiane.dto.NotificationDTO;
import com.jiane.dto.QuestionDTO;
import com.jiane.enums.NotificationTypeEnum;
import com.jiane.mapper.NotificationMapper;
import com.jiane.mapper.QuestionMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Notification;
import com.jiane.model.Question;
import com.jiane.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    public List<NotificationDTO> getNotifications(Integer start, Integer end, User user) {

        List<Notification> notifications = notificationMapper.getNotifications(start, end, user.getId());
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (int i = 0; i < notifications.size(); i++) {
            Integer replierId = notifications.get(i).getReplierId();
            Integer questionId = notifications.get(i).getQuestionId();
            User userById = userMapper.findUserById(replierId);
            Question questionById = questionMapper.findQuestionById(questionId);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notifications.get(i),notificationDTO);

            notificationDTO.setUser(userById);
            notificationDTO.setQuestion(questionById);
            Integer type = notifications.get(i).getType();
            if (type==0){
                notificationDTO.setTypeName(NotificationTypeEnum.REPLY_QUESTION.getName());
            }else if (type==1){
                notificationDTO.setTypeName(NotificationTypeEnum.REPLY_COMMENT.getName());
            }
            notificationDTOS.add(notificationDTO);
        }


        return notificationDTOS;
    }

    public Integer findUnReadCountsByUser(User user) {
        return notificationMapper.findUnReadCountsByUser(user.getId());
    }

    public Integer findAllNotificationCountsByUser(User user) {
        return notificationMapper.findAllNotificationCountsByUser(user.getId());
    }
}
