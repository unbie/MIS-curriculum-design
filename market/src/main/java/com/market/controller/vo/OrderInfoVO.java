package com.market.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoVO {

    private Integer orderId;
    private Long orderNo;
    private String imgUrl;
    private String description;
    private BigDecimal totalPrice;
    private Integer orderStatus;

}
