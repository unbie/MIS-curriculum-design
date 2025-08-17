package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.INPUT)
    @ApiModelProperty(value = "评论序号")
    private Integer commentId;

    @ApiModelProperty(value = "评论者")
    private Integer reviewerId;

    @ApiModelProperty(value = "被评论人")
    private Integer reviewedId;

    @ApiModelProperty(value = "是否回复")
    private Integer responseOrdinal;

    @ApiModelProperty(value = "星级")
    private Integer stars;

    @ApiModelProperty(value = "用户提交的初始评论")
    private String initialComments;

    @ApiModelProperty(value = "关键词屏蔽后的评论")
    private String modifiedComments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "评价时间")
    private Date timestamp;

    @ApiModelProperty(value = "追评")
    private String twiceComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
