package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentPartVo {
    //主键 传递但是不在前端界面显示
    private Integer commentId;

    //person
    private String reviewerName;

    private String reviewedName;

    //comment
    private Integer responseOrdinal;

    private Integer stars;

    private String initialComments;

    private String modifiedComments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    private String twiceComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
