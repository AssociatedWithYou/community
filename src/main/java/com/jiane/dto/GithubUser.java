package com.jiane.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GithubUser {
    private String name;
    private String id;
    private String bio;//github返回的用户数据中的用户的描述信息

}
