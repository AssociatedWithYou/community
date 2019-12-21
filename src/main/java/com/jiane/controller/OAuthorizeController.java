package com.jiane.controller;

import com.jiane.dto.AccessTokenDTO;
import com.jiane.dto.GithubUser;
import com.jiane.mapper.UserMapper;
import com.jiane.model.User;
import com.jiane.provice.GithubProvider;
import com.jiane.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
public class OAuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    String client_id;

    @Value("${github.client.secret}")
    String client_secret;

    @Value("${github.redirect.uri}")
    String redirect_uri;



    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpSession session,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);//申请的github的Oauthor app的id
        accessTokenDTO.setClient_secret(client_secret);//申请的github的oauthor app的secret
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);//git回调我们自己的方法
        accessTokenDTO.setState(state);
        String accessTokenByGithub = githubProvider.getAccessTokenByGithub(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessTokenByGithub);

        System.out.println(githubUser);

//        System.out.println(githubUser.getId()+"___"+githubUser.getName()+"___"+githubUser.getBio());
        if (githubUser == null) {
//            登陆失败
            return "redirect:/";
        }else{
//            登陆成功

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
            String format = simpleDateFormat.format(date);

            log.info("id为"+githubUser.getId()+"的用户于"+format+"成功登陆本社区");
            User user = new User();

            user.setName(githubUser.getName())
                    .setAccountId(githubUser.getId())
                    .setAvatarUrl(githubUser.getAvatar_url());

            User user1 = userService.addOrUpdateUser(user);

            Cookie cookie = new Cookie("token", user1.getToken());
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            return "redirect:/";
        }
    }



    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request , HttpServletResponse response){
        request.getSession().removeAttribute("user");

        //移出Cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
