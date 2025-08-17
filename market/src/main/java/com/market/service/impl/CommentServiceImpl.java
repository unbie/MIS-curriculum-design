package com.market.service.impl;

import com.market.controller.dto.CommentDTO;
import com.market.controller.vo.CommentPartVo;
import com.market.controller.vo.CommentVo;
import com.market.controller.vo.SensitivePartVo;
import com.market.entity.Comment;
import com.market.mapper.CommentMapper;
import com.market.mapper.PersonMapper;
import com.market.mapper.SensitiveWordMapper;
import com.market.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private SensitiveWordMapper sensitiveWordMapper;
    @Resource
    private PersonMapper personMapper;

    //评论管理初始页面显示
    //并没有用到此方法
    //实际界面展示采用另外两个Vo类进行操作
    @Override
    public List<CommentVo> listC() {
        List<CommentVo> commentVoList;
        //从CommentPartVo中获得值
        return null;
    }


    //实际页面展示用以下两个list方法进行
    //找到关于Comment的部分Part
    @Override
    public List<CommentPartVo> listCP() {
        List<CommentPartVo> commentPartVoList = commentMapper.listCommentPartVo();
        //对初始评论进行敏感词屏蔽
        for (CommentPartVo commentPartVo : commentPartVoList) {
            commentPartVo.setModifiedComments(blockSensitiveWords(commentPartVo.getInitialComments()));
        }
        return commentPartVoList;
    }

    //找到关于SensitiveWord的部分Part
    @Override
    public List<SensitivePartVo> listSP() {
        return sensitiveWordMapper.listSensitiveWordVo();
    }

    //新增评论方法
    @Override
    public Comment saveOrUpdate(CommentPartVo commentPartVo) {
        Comment comment = new Comment();
        //将前端传过来的CommentPartVo转化为Comment实体类
        comment.setCommentId(commentPartVo.getCommentId());
        comment.setReviewerId(commentMapper.selectIdByName1(commentPartVo.getReviewerName()));
        comment.setReviewedId(commentMapper.selectIdByName2(commentPartVo.getReviewedName()));
        comment.setResponseOrdinal(commentPartVo.getResponseOrdinal());
        comment.setStars(commentPartVo.getStars());
        comment.setInitialComments(commentPartVo.getInitialComments());
        comment.setModifiedComments(blockSensitiveWords(comment.getInitialComments()));
        comment.setTimestamp(commentPartVo.getTimestamp());
        comment.setTwiceComment(commentPartVo.getTwiceComment());
        comment.setCreateTime(commentPartVo.getCreateTime());

        return null;
    }

    //敏感词屏蔽功能
    //输入初始评论，读取后台敏感词，根据敏感词进行屏蔽，返回修改后的评论
    public String blockSensitiveWords(String initialComment) {
        String dirtyComment = "";
        //读取所有的敏感词
        List<String> words = sensitiveWordMapper.listSensitiveWord();

        //根据敏感词进行屏蔽
        dirtyComment = initialComment;
        String replacement;
        for (String sensitiveWord : words) {
            replacement = "";
            for (int i1 = 0; i1 < sensitiveWord.length(); i1++) {
                replacement += "*";
            }
            dirtyComment = dirtyComment.replace(sensitiveWord, replacement);
        }

        //返回修改后的评论
        return dirtyComment;
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        Integer maxCommentId = commentMapper.getMaxCommentId();
        Integer newCommentId = maxCommentId != null ? maxCommentId + 1 : 1;

        String reviewerAccount = commentDTO.getReviewerAccount();
        String reviewedAccount = commentDTO.getReviewedAccount();

        Integer reviewerId = personMapper.findPersonIdByAccount(reviewerAccount);
        Integer reviewedId = personMapper.findPersonIdByAccount(reviewedAccount);

        if (reviewerId != null && reviewedId != null) {
            Comment comment = new Comment();
            comment.setCommentId(newCommentId);
            comment.setReviewerId(reviewerId);
            comment.setReviewedId(reviewedId);
            comment.setInitialComments(commentDTO.getInitialComments());
            comment.setModifiedComments(blockSensitiveWords(comment.getInitialComments()));
            comment.setStars(commentDTO.getStars());
            comment.setTimestamp(new Date());
            comment.setCreateTime(new Date());
            commentMapper.insert(comment);
        } else {
            // 处理用户ID查询失败的情况

        }
    }

}
