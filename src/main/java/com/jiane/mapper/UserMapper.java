package com.jiane.mapper;

import com.jiane.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(" +
            "#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);


    @Select("select id , name , account_id accountId , token , gmt_create gmtCreate , gmt_modified gmtModified ,avatar_url avatarUrl from `USER` where token = #{token}")
    User findByToken(String token);

    @Select("select id , name , account_id accountId , token , gmt_create gmtCreate , gmt_modified gmtModified ,avatar_url avatarUrl from `USER` where id = #{creator}")
    User findUserById(@Param("creator") Integer creator);

    @Select("select id , name , account_id accountId , token , gmt_create gmtCreate , gmt_modified gmtModified ,avatar_url avatarUrl from `USER` where account_id = #{accountId}")
    User findUserByAcount(User user);
}
