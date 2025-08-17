package com.market.controller.vo;

import com.market.entity.Admin;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminVo {

    private Integer adminId;


    private String adminUsername;


    private String password;


    private Integer sex;


    private Long phone;


    private Integer isLocked;


    private Integer roleLevel;


    private LocalDateTime createTime;

    public AdminVo(Admin admin) {
        adminId = admin.getAdminId();
        adminUsername = admin.getAdminUsername();
        password = admin.getPassword();
        sex = admin.getSex();
        phone = admin.getPhone();
        isLocked = admin.getIsLocked();
        roleLevel = admin.getRoleLevel();
        //createTime=admin.getCreateTime();
    }


}
