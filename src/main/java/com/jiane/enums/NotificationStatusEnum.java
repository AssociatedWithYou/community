package com.jiane.enums;



public enum NotificationStatusEnum {
    UNREAD_NOTIFICATION(0,"未读回复"),
    READ_NOTIFICATION(1,"已读回复");

    private int status;
    private String name;

    NotificationStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
