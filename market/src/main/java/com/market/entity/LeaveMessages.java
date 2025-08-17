package com.market.entity;

import java.beans.Transient;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *

 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LeaveMessages对象", description = "")
public class LeaveMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    @ApiModelProperty(value = "评论序号")
    private Integer commentId;

    @ApiModelProperty(value = "评论者")
    private Integer reviewerId;

    @ApiModelProperty(value = "父级评论的id")
    private Integer parentId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "商品id")
    private Integer goodId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户名称")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "回复对象")
    private String target;


    @ApiModelProperty(value = "商品名称")
    private String goodName;

    @TableField(exist = false)
    private List<LeaveMessages> children;

    @TableField(exist = false)
    private String reviewerName;


}
