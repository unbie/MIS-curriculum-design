package com.market.controller.vo;

import com.market.entity.Good;
import lombok.Data;

@Data
public class GoodWithImgVO {
    private Good good;
    private String imgUrl;
}
