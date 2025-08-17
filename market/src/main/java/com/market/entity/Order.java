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
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.INPUT)
    @ApiModelProperty(value = "订单序号")
    private Integer orderId;

    @ApiModelProperty(value = "订单号")
    private Long orderNo;

    @ApiModelProperty(value = "商品号")
    private Integer goodId;

    @ApiModelProperty(value = "订单收货地址id")
    private Integer addressId;

    @ApiModelProperty(value = "卖家Id")
    private Integer sellerId;

    //卖家姓名
    @TableField(exist = false)
    private String sellerName;

    @ApiModelProperty(value = "订单所属客户Id")
    private Integer customerId;

    //买家姓名
    @TableField(exist = false)
    private String customerName;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "订单状态:0.待支付 1.已支付 2.出库成功 3.交易成功 -1.手动关闭 -2.商家关闭 -3买家关闭")
    private Integer orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "0.无 1.支付宝支付 2.微信支付")
    private Integer payWays;

    @ApiModelProperty(value = "支付状态:0.未支付,1.支付成功,-1:支付失败")
    private Integer payStatus;

    @ApiModelProperty(value = "商品数量")
    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
