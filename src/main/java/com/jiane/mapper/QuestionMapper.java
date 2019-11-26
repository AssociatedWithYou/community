package com.jiane.mapper;

import com.jiane.model.Question;
import com.jiane.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values(#{title},#{description}," +
            "#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    public void createQuestion(Question question);

    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            "view_count viewCount,like_count likeCount,tags from question limit #{start},#{end}")
    List<Question> findQuestionByPage(@Param("start") Integer start, @Param("end")Integer end);

    @Select("select count(1) from question")
    Integer findCounts();

    @Select("select count(1) from question where creator = #{id}")
    Integer findCountsByUser(User user);


    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            "view_count viewCount,like_count likeCount,tags from question where creator = #{creatorId} limit #{start},#{end}")
    List<Question> findQuestionByUser(@Param("start") Integer start, @Param("end")Integer end,@Param("creatorId") Integer creatorId);
}