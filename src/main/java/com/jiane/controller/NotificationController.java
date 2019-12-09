package com.jiane.controller;

import com.jiane.enums.NotificationStatusEnum;
import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import com.jiane.mapper.NotificationMapper;
import com.jiane.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {

    @Autowired
    NotificationMapper notificationMapper;

    @GetMapping("/notification/{id}")
    public String updateNotificationStatus(@PathVariable("id") Integer id) {
        Notification notification = notificationMapper.findNotificationById(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getStatus() == NotificationStatusEnum.UNREAD_NOTIFICATION.getStatus()) {
            notificationMapper.updateNotificationStatus(id);
        }
        return "redirect:/questions/" + notification.getQuestionId();
    }
}
