package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "Carousel对象", description = "")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "carousel_id", type = IdType.INPUT)
    @ApiModelProperty(value = "首页轮播图主键id")
    private Integer carouselId;

    @TableField(value = "carousel_url")
    @ApiModelProperty(value = "轮播图")
    private String carouselUrl;

    @TableField(value = "redirect_url")
    @ApiModelProperty(value = "点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    @TableField(value = "carousel_rank")
    @ApiModelProperty(value = "排序值(字段越大越靠前)")
    private Integer carouselRank;

    @TableField(value = "is_deleted")
    @ApiModelProperty(value = "删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
