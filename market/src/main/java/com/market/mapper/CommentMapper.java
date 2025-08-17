package com.market.mapper;

import com.market.controller.vo.CommentPartVo;
import com.market.controller.vo.CommentVo;
import com.market.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    //获取评论有关信息
    List<CommentPartVo> listCommentPartVo();

    //根据person的Name获取对应的id
    //reviewer_id
    Integer selectIdByName1(String name);

    //reviewed_id
    Integer selectIdByName2(String name);

    @Select("SELECT MAX(comment_id) FROM comment")
    @ResultType(Integer.class)
    Integer getMaxCommentId();
}
