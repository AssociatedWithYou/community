package com.jiane.dto;

import com.jiane.exception.CustomizeErrorCode;
import com.jiane.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        return new ResultDTO(code, message);
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return new ResultDTO(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO successOf(CustomizeErrorCode errorCode) {
        return new ResultDTO(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }
}
