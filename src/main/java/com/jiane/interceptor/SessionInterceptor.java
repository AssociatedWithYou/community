package com.jiane.interceptor;


import com.jiane.mapper.NotificationMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器执行了");
        User user = (User)request.getSession().getAttribute("user");
        if (user!=null&&user.getId()!=null){
            Integer unReadCountsByUser = notificationMapper.findUnReadCountsByUser(user.getId());
            request.getSession().setAttribute("unReadCount",unReadCountsByUser);
            System.out.println("通知数："+unReadCountsByUser);
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length==0){
            System.out.println("1");
            return true;
        }else {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    System.out.println("A");
                    user = userMapper.findByToken(token);
                    System.out.println("B");
                    System.out.println(user);
                    if (user != null) {
                        Integer unReadCountsByUser = notificationMapper.findUnReadCountsByUser(user.getId());
                        request.getSession().setAttribute("unReadCount",unReadCountsByUser);
                        request.getSession().setAttribute("user", user);
                        System.out.println("通知数："+unReadCountsByUser);
                    }
                    break;
                }
            }
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
