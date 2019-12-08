package com.jiane.model;

import com.jiane.dto.TagsDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Tag {

    public static List<TagsDTO> getTags(){
        List<TagsDTO> tagsDTOS = new ArrayList<>();
        TagsDTO tagsDTO1 = new TagsDTO();
        tagsDTO1.setTagCategoryName("开发语言");
        tagsDTO1.setTags(Arrays.asList("Java","Javascript","php","css","html","html5","node.js","python","c++","c","go"));

        TagsDTO tagsDTO2 = new TagsDTO();
        tagsDTO2.setTagCategoryName("平台框架");
        tagsDTO2.setTags(Arrays.asList("laravel","Spring","Struts2","Hibernate","SpringBoot","SpringCloud","SpringMVC","express","django","flask","yii","ruby-on-rails","tornado","koa"));

        TagsDTO tagsDTO3 = new TagsDTO();
        tagsDTO3.setTagCategoryName("服务器");
        tagsDTO3.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","负载均衡","tomcat","unix","hadoop","window-server"));

        TagsDTO tagsDTO4 = new TagsDTO();
        tagsDTO4.setTagCategoryName("数据库");
        tagsDTO4.setTags(Arrays.asList("Mysql","Oracle","H2","Redis","Mongodb","sql","Memcached","sqlserver","postgresql","sqlite"));

        TagsDTO tagsDTO5 = new TagsDTO();
        tagsDTO5.setTagCategoryName("开发工具");
        tagsDTO5.setTags(Arrays.asList("Git","GitHub","visual-studio-code","vim","sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atomemacs","textmate","hg"));
        tagsDTOS.add(tagsDTO1);
        tagsDTOS.add(tagsDTO2);
        tagsDTOS.add(tagsDTO3);
        tagsDTOS.add(tagsDTO4);
        tagsDTOS.add(tagsDTO5);
        return tagsDTOS;
    }

}


                      



