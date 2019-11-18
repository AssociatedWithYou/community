package com.jiane.controller;

import com.jiane.dto.AccessTokenDTO;
import com.jiane.dto.GithubUser;
import com.jiane.provice.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    String client_id;

    @Value("${github.client.secret}")
    String client_secret;

    @Value("${github.redirect.uri}")
    String redirect_uri;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);//申请的github的Oauthor app的id
        accessTokenDTO.setClient_secret(client_secret);//申请的github的oauthor app的secret
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);//git回调我们自己的方法
        accessTokenDTO.setState(state);
        String accessTokenByGithub = githubProvider.getAccessTokenByGithub(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessTokenByGithub);
        System.out.println(githubUser.getId()+"___"+githubUser.getName()+"___"+githubUser.getBio());
        return "index";
    }
}
