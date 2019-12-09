package com.jiane.mapper;

import com.jiane.model.Notification;
import com.jiane.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(gmt_create,type,status,replier_id,by_replier_id,question_id) values(#{gmtCreate},#{type},#{status},#{replierId},#{byReplierId},#{questionId})")
    void addNotication(Notification notification);


    @Select("select count(id) from notification where by_replier_id = #{userId}")
    Integer findAllNotificationCountsByUser(@Param("userId") Integer userId);

    @Select("select id,gmt_create gmtCreate,type ,status,replier_id replierId,by_replier_id byReplierId,question_id questionId from notification where by_replier_id = #{userId} order by gmt_create desc limit #{start},#{end}")
    List<Notification> getNotifications(@Param("start") Integer start, @Param("end") Integer end, @Param("userId") Integer userId);


    @Select("select count(id) from notification where by_replier_id = #{userId} and status = 0")
    Integer findUnReadCountsByUser(@Param("userId") Integer userId);

    @Select("select id,gmt_create gmtCreate,type ,status,replier_id replierId,by_replier_id byReplierId,question_id questionId from notification where id = #{id}")
    Notification findNotificationById(@Param("id")Integer id);

    @Update("update notification set status = 1 where id = #{id}")
    void updateNotificationStatus(@Param("id")Integer id);

   /* @Update("update notification set status = 1 where question_id = #{id}")
    void updateNotificationStatusByQuestionId(@Param("id") Integer id);*/
}
