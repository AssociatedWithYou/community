package com.jiane.mapper;

import com.jiane.model.Question;
import com.jiane.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {


    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            "view_count viewCount,like_count likeCount,tags from question where id = ${i}")
    Question selectQuestionById(@Param("i") Integer i);


    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values(#{title},#{description}," +
            "#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    public void createQuestion(Question question);

    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            "view_count viewCount,like_count likeCount,tags from question order by gmt_create desc limit #{start},#{end} ")
    List<Question> findQuestionByPage(@Param("start") Integer start, @Param("end")Integer end);

    @Select("select count(1) from question")
    Integer findCounts();

    @Select("select count(1) from question where creator = #{id}")
    Integer findCountsByUser(User user);


    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            "view_count viewCount,like_count likeCount,tags from question where creator = #{creatorId} limit #{start},#{end} ")
    List<Question> findQuestionByUser(@Param("start") Integer start, @Param("end")Integer end,@Param("creatorId") Integer creatorId);

    @Select("select id,title,description,creator,gmt_create gmtCreate ,gmt_modified gmtModified,comment_count commentCount," +
            " view_count viewCount,like_count likeCount,tags from question where id = #{id}")
    Question findQuestionById(@Param("id") Integer id);

    @Update("update question set title = #{title} , description = #{description} , tags = #{tags} ,gmt_modified = #{gmtModified} where id = #{id}")
    Integer updateQuestion(Question question);

    @Update("update question set view_count = view_count + #{i}")
    void updateViewCount(@Param("i") int i);

    @Update("update question set comment_count = comment_count+ 1 where id = #{id}")
    void updateCommentCount(Question question);
}
