package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market.entity.SensitiveWord;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
public class CommentVo {

    //CommentPartVo
    private List<CommentPartVo> commentPartVos;

    //set get方法
    public void setCommentPartVos(List<CommentPartVo> commentPartVos) {
        this.commentPartVos = commentPartVos;
    }

    public List<CommentPartVo> getCommentPartVo() {
        return this.commentPartVos;
    }

    private List<SensitivePartVo> sensitivePartVos;

    //set get方法
    public void setSensitivePartVo(List<SensitivePartVo> sensitivePartVos) {
        this.sensitivePartVos = sensitivePartVos;
    }

    public List<SensitivePartVo> getSensitivePartVo() {
        return sensitivePartVos;
    }

}
