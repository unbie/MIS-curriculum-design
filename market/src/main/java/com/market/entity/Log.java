package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "Log对象", description = "")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.INPUT)
    @ApiModelProperty(value = "日志id")
    private Integer logId;

    @ApiModelProperty(value = "管理员/用户的编号 规定：管理员编号从1~100，用户编号从1000开始")
    private Integer userId;

    @ApiModelProperty(value = "IP地址（湖南、河北...）")
    @TableField("IP")
    private String ip;

    @ApiModelProperty(value = "操作系统名称")
    private String os;

    @ApiModelProperty(value = "浏览器名称")
    private String browser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "登录日期时间（年月日时分秒）")
    private Date loginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
