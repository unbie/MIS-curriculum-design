package com.market.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {


    private Long orderNo;
    private String goodName;
    private String goodImg;
    private String customerAccount;
    private String sellerAccount;
    private BigDecimal totalPrice;
    private Long shoppingPhone;
    private String shippingAddress;
    private String initialComments;
    private String modifiedComments;
    private Integer stars;
    private Integer orderStatus;

}
