package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market.entity.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 */
//商品管理 订单管理 求购信息管理 用户管理
@Data
public class GoodVo {

    private Integer goodId;


    private String goodName;


    private Integer categoryId;


    private String description;


    private BigDecimal price;


    private BigDecimal expressPrice;


    private Integer counts;


    private Integer publisherId;


    private Integer isSale;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private List<String> image;

    public GoodVo() {
    }

    ;


    public GoodVo(Good good, List<String> imgAndGood) {
        goodId = good.getGoodId();
        goodName = good.getGoodName();
        categoryId = good.getCategoryId();
        description = good.getDescription();
        price = good.getPrice();
        expressPrice = good.getExpressPrice();
        counts = good.getCounts();
        publisherId = good.getPublisherId();
        createTime = good.getCreateTime();
        image = imgAndGood;
        isSale = good.getIsSale();


    }


}
