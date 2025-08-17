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
@ApiModel(value = "PersonShoppingAddress对象", description = "")
public class PersonShoppingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shopping_address_id", type = IdType.INPUT)
    @ApiModelProperty(value = "收货地址id")
    private Integer shoppingAddressId;

    @ApiModelProperty(value = "用户id")
    private Integer personId;

    @ApiModelProperty(value = "用户收货地址（可以有多个）")
    private String shippingAddress;

    @ApiModelProperty(value = "收件人姓名")
    private String shoppingName;

    @ApiModelProperty(value = "用户收货电话号码")
    private Long shoppingPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
