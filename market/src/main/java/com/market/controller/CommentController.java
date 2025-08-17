package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.market.common.Result;
import com.market.controller.dto.CommentDTO;
import com.market.controller.vo.CommentPartVo;
import com.market.controller.vo.CommentVo;
import com.market.controller.vo.SensitivePartVo;
import com.market.entity.Comment;
import com.market.service.CommentService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    //最初界面展示思路
    @GetMapping("/listC")
    public Result listC() {
        List<CommentVo> commentVoList = commentService.listC();
        return Result.success(commentVoList, (long) commentVoList.size());
    }

    //将评论管理页面分为CommentPartVo和SensitivePartVo两部分展示
    //CommentPartVo部分
    //默认展示到评论管理初始页面的部分
    @GetMapping("/listCP")
    public Result listCP(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<CommentPartVo> commentPartVoList = commentService.listCP();
            return Result.success(commentPartVoList, (long) commentPartVoList.size());

        } else {
            return Result.fail();
        }
    }

    //SensitivePartVo部分
    //展示到敏感词管理的部分
    @GetMapping("/listSP")
    public Result listSP(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<SensitivePartVo> sensitivePartVoList = commentService.listSP();
            return Result.success(sensitivePartVoList, (long) sensitivePartVoList.size());

        } else {
            return Result.fail();
        }
    }

    //删除评论
    @GetMapping("/deleteComment")
    public Result deleteComment(@RequestParam("commentId") List<Integer> commentIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer commentId : commentIdList) {
                commentService.removeById(commentId);
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    //新增评论 用户端功能 后台管理员无法新增评论
    //用户端新增评论调用此接口
    @PostMapping("/add")
    public Result add(@RequestBody CommentPartVo commentPartVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return commentService.save(commentService.saveOrUpdate(commentPartVo)) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }


    //获取某个personId所对应的所有评论
    @GetMapping("/getCommentById")
    public Result getCommentById(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            //在评论表中找到personId所对应的所有评论 personId作为被评论人
            LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Comment::getReviewedId, personId);
            List<Comment> commentList = commentService.list(wrapper);
            return Result.success(commentList, (long) commentList.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 新增评论（用户端）
     */
    @PostMapping("/addComment")
    public Result addComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            commentService.addComment(commentDTO);
            return Result.success();
        } else {
            return Result.fail();
        }
    }
}
