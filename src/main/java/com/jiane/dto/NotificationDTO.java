package com.jiane.dto;

import com.jiane.model.Question;
import com.jiane.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Integer id;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private Integer replierId;
    private Integer byReplierId;
    private Integer questionId;
    private User user;
    private Question question;
    private String typeName;
}
