package com.market.service;

import com.market.controller.dto.CommentDTO;
import com.market.controller.vo.CommentPartVo;
import com.market.controller.vo.CommentVo;
import com.market.controller.vo.SensitivePartVo;
import com.market.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface CommentService extends IService<Comment> {
    List<CommentVo> listC();

    //Comment listC();
    List<CommentPartVo> listCP();

    List<SensitivePartVo> listSP();

    //新增评论
    Comment saveOrUpdate(CommentPartVo commentPartVo);

    void addComment(CommentDTO commentDTO);
}
