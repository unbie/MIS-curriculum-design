package com.market.service;

import com.market.entity.Admin;
import com.market.entity.Person;
import com.market.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface SysLogService extends IService<SysLog> {

    //新增日志
    SysLog addSysLog(Admin admin, HttpServletRequest request);

    SysLog addSysLog(Person person, HttpServletRequest request);
}
