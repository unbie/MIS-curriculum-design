package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 学校配置的商品VO
 * {
 * campus_id: 1,
 * campus_name: '武汉大学'
 * },
 */
@Data
public class CampusVO {

    private Integer campusId;

    private String campusName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
