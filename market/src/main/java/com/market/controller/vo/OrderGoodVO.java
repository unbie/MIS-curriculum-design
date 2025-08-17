package com.market.controller.vo;

import lombok.Data;

@Data
public class OrderGoodVO {
    //Good
    private Integer goodId;
    private String goodName;
    private String goodImg;
    private String description;
}
