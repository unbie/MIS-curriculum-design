package com.market.controller.dto;


import lombok.Data;

@Data
public class OrderStatusDTO {
    private Integer orderId;
    private Integer orderStatus;
    private Integer payStatus;
}
