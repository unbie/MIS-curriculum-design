package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.market.controller.vo.GoodWithImgVO;
import com.market.controller.vo.GoodWithOneImgVO;
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
@ApiModel(value = "Shoppingcart对象", description = "")
public class Shoppingcart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cart_id", type = IdType.INPUT)
    @ApiModelProperty(value = "用户购物车itemid")
    private Integer cartId;

    @ApiModelProperty(value = "用户编号")
    private Integer customerId;

    @ApiModelProperty(value = "商品编号")
    private Integer goodId;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    private GoodWithOneImgVO good;


}
