package com.jiane.provice;


import com.alibaba.fastjson.JSON;
import com.jiane.dto.AccessTokenDTO;
import com.jiane.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class GithubProvider {

    /**
     * 获取AccessToken访问令牌
     *
     * @param accessTokenDTO
     * @return
     */
    public String getAccessTokenByGithub(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        String json = JSON.toJSONString(accessTokenDTO);

        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")//git的api
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code()==200){
                String stringForGit = response.body().string();//stringForGit: access_token=1242e36a9baa95241c7951094609c34808353577&scope=user&token_type=bearer
                String[] split1 = stringForGit.split("&");
                String[] split2 = split1[0].split("=");
                String finalAccessToken  = split2[1];
                return finalAccessToken;
            }else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getGithubUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();

        Date date = new Date();
        System.out.println("https://api.github.com/user?access_token="+accessToken);
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
           if (response.code()==200){
               String string = response.body().string();
               System.out.println(string);
               GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
               return githubUser;
           }else {
               return null;
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
