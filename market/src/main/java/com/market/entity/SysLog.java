package com.market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *

 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLog对象", description = "")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志序号")
    @TableId(value = "logID", type = IdType.AUTO)
    private Integer logId;

    @ApiModelProperty(value = "用户账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty(value = "操作内容")
    @TableField("Log_Content")
    private String logContent;

    @ApiModelProperty(value = "登陆IP地址")
    @TableField("IP_Address")
    private String ipAddress;

    @ApiModelProperty(value = "用户电脑操作系统 ")
    @TableField("OS")
    private String os;

    @ApiModelProperty(value = "用户用的浏览器")
    @TableField("Browser")
    private String browser;

    @ApiModelProperty(value = "日志创建时间")
    @TableField("CreateDate")
    private Date createdate;

    @ApiModelProperty(value = "备注")
    @TableField("Remark")
    private String remark;


}
