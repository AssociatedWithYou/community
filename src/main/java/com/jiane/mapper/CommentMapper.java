package com.jiane.mapper;

import com.jiane.dto.CommentListDTO;
import com.jiane.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) " +
            " values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    public void addComment(Comment comment);

    @Select("select id,parent_id parentId,type,commentator,gmt_create gmtCreate,gmt_modified gmtModified,like_count likeCount,content from comment where id = #{parentId} and type = #{type}")
    Comment selectCommentByParentId(@Param("parentId") Long parentId, @Param("type") Integer type);

    @Select("select id,parent_id parentId,type,commentator,gmt_create gmtCreate,gmt_modified gmtModified,like_count likeCount,content from comment where parent_id = #{id} and type = #{question} order by gmt_create desc")
    List<CommentListDTO> findAllParentCommentsByQuestionIdAndType(@Param("id") Integer id, @Param("question") Integer question);

}
