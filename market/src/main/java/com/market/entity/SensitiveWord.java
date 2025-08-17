package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SensitiveWord对象", description = "")
public class SensitiveWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sensitive_id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer sensitiveId;

    @ApiModelProperty(value = "敏感词")
    private String word;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
