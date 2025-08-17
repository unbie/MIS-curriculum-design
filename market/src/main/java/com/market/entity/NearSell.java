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
@ApiModel(value = "NearSell对象", description = "")
public class NearSell implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "campus_id", type = IdType.INPUT)
    @ApiModelProperty(value = "学校id")
    private Integer campusId;

    @ApiModelProperty(value = "学校名称")
    private String campusName;

    @ApiModelProperty(value = "商品1")
    private Integer goodId1;

    @ApiModelProperty(value = "商品2")
    private Integer goodId2;

    @ApiModelProperty(value = "商品3")
    private Integer goodId3;

    @ApiModelProperty(value = "商品4")
    private Integer goodId4;

    @ApiModelProperty(value = "商品5")
    private Integer goodId5;

    @ApiModelProperty(value = "商品6")
    private Integer goodId6;

    @ApiModelProperty(value = "商品7")
    private Integer goodId7;

    @ApiModelProperty(value = "商品8")
    private Integer goodId8;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
