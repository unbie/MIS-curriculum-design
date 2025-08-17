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
@ApiModel(value = "Person对象", description = "")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "person_id", type = IdType.INPUT)
    @ApiModelProperty(value = "用户编号")
    private Integer personId;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    @ApiModelProperty(value = "用户所在学校id")
    private Integer campusId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "注册日期")
    private Date registerDate;

    @ApiModelProperty(value = "用户是否被封锁 0表示未锁定，1表示锁定")
    private Integer isLocked;

    @ApiModelProperty(value = "用户头像url")
    private String avatarUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
