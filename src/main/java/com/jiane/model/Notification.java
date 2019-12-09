package com.jiane.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Integer id;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private Integer replierId;
    private Integer byReplierId;
    private Integer questionId;
}
