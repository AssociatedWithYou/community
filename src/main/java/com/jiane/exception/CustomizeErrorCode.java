package com.jiane.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    COMMENT_RELEASE_SUCCESS(200,"评论发布成功!"),
    USER_OFFLINE(1001,"请先登录后在评论!"),
    QUESTION_NOT_FOUND(2001, "抱歉,你要找的问题没有找到,请稍后再试!"),
    TARGET_COMMENT_PARAM_NOT_EXISTS(2002, "回复的问题对象不明确!"),
    SERVER_ERROR(3001,"抱歉,服务器太忙了,请稍后再试!"),
    TYPE_PARAM_WRONG(2003,"评论的类型异常!"),
    COMMENT_NOT_FOUND(2004,"父级评论不存在!"),
    CONTENT_IS_NULL(2005,"内容不能为空!"),
    NOTIFICATION_NOT_FOUND(2006,"没有找到当前回复通知!");



    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
