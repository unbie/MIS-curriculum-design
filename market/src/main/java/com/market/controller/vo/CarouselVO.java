package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 轮播图VO类
 * { id : 1,
 * image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
 * redirectUrl: 'https://example.com',
 * carouselRank: 1  ,
 * addTime: '2023-06-28'
 * },
 */
@Data
public class CarouselVO {

    private Integer carouselId;

    private String carouselUrl;

    private String redirectUrl;

    private Integer carouselRank;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
