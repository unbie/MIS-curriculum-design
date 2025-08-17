package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Good对象", description = "")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "good_id", type = IdType.INPUT)
    @ApiModelProperty(value = "商品编号")
    private Integer goodId;

    @ApiModelProperty(value = "商品名字")
    private String goodName;

    @ApiModelProperty(value = "商品类别")
    private Integer categoryId;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "邮费")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "数量")
    private Integer counts;

    @ApiModelProperty(value = "发布人")
    private Integer publisherId;

    @ApiModelProperty(value = "需要或售出")
    private Integer isSale;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
