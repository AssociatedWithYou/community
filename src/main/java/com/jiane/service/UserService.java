package com.jiane.service;

import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User addOrUpdateUser(User myUser){
        User user = new User();
        User userByAcountId = userMapper.findUserByAcount(myUser);
        if (userByAcountId.getId()==null) {
            Long sysTime = System.currentTimeMillis();
            String token = UUID.randomUUID().toString();
            user.setToken(token)
                .setGmtCreate(sysTime)
                .setGmtModified(sysTime);


            userMapper.insert(myUser);
            return myUser;
        }
        return userByAcountId;
    }

}
