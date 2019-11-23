package com.jiane.mapper;

import com.jiane.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(" +
            "#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);


    @Select("select id , name , account_id accountId , token , gmt_create gmtCreate , gmt_modified gmtModified from user where token = #{token}")
    User findByToken(String token);
}
