package com.jiane.service;

import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
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


    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User findUserByCommentator(Integer aInteger) {
        User userById = userMapper.findUserById(aInteger);
        return userById;
    }

}
