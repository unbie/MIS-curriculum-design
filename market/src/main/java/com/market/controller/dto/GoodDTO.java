package com.market.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodDTO {
    private Integer goodId;
    private String goodName;
    private String imgUrl;
    private BigDecimal price;
}
