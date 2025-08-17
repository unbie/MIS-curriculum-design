package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单控制
 * </p>
 * <p>
 * 初始界面关于订单的VO类
 * </p>
 */
@Data
public class OrderVo {
    //Order
    private Integer orderId;
    private Long orderNo;
    private Integer goodId;
    private Integer addressId;
    private Integer sellerId;
    private Integer customerId;
    private BigDecimal totalPrice;
    private Integer orderStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    private Integer payWays;
    private Integer payStatus;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
