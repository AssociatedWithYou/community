package com.jiane.mapper;

import com.jiane.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) " +
            " values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    public void addComment(Comment comment);

    @Select("select id,parent_id parentId,type,commentator,gmt_create gmtCreate,gmt_modified gmtModified,like_count likeCount,content from comment where parent_id = #{parentId}")
    Comment selectCommentByParentId(@Param("parentId") Long parentId);
}
