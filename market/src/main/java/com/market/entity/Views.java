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

 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Views对象", description = "")
public class Views implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "view_id", type = IdType.AUTO)
    @ApiModelProperty(value = "浏览量id")
    private Integer viewId;

    @ApiModelProperty(value = "浏览的商品种类，通过商品种类向用户推荐不同类型的商品")
    private Integer categoryId;

    private Integer personId;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
