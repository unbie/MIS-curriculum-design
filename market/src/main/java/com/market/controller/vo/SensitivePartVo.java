package com.market.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market.entity.SensitiveWord;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SensitivePartVo {
    //sensitiveWords
    private Integer sensitiveId;
    private String word;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
