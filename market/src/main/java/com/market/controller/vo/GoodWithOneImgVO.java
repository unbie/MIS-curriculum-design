package com.market.controller.vo;

import com.market.entity.Good;
import lombok.Data;

/**
 * 用户端个人详情界面的走马灯图片以及所对应的商品
 */
@Data
public class GoodWithOneImgVO {
    private Good good;
    private String imgUrl;
}
