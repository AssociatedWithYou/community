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

        User userByAcountId = userMapper.findUserByAcount(myUser);
        if (userByAcountId==null||userByAcountId.getId()==null) {
            Long sysTime = System.currentTimeMillis();
            String token = UUID.randomUUID().toString();
            myUser.setToken(token)
                .setGmtCreate(sysTime)
                .setGmtModified(sysTime);

            if (myUser.getName()==null||myUser.getName().isEmpty()){
                String name = "用户"+UUID.randomUUID().toString().replace("-","").substring(0,6);
                myUser.setName(name);
            }


            userMapper.insert(myUser);
            myUser.setToken(token).setGmtCreate(sysTime).setGmtModified(sysTime);
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
