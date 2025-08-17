package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;;
import lombok.Data;

import java.util.Date;

/**
 * 一学校配置的商品
 * {
 * campus_id: 1,
 * good_id:1,
 * good_name: '商品1',
 * good_image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
 * createTime:'2006-12-01 00:00:00',
 * },
 * <p>
 * 也可以是该学校所拥有的所有商品的VO
 * { campus_id: 1,
 * good_id:1,
 * good_name: '商品1',
 * good_image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
 * createTime:'2006-12-01 00:00:00'
 * }
 */
@Data
public class CampusGoodIsConfiguredVO {

    private Integer campusId;

    private Integer goodId;

    private String goodName;

    private String goodImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
