package com.market.service.impl;

import com.market.entity.Admin;
import com.market.entity.Person;
import com.market.entity.SysLog;
import com.market.mapper.SysLogMapper;
import com.market.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-07-02
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;


    @Override
    public SysLog addSysLog(Admin admin, HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        String agentStr = request.getHeader("user-agent");
        UserAgent agent = UserAgent.parseUserAgentString(agentStr);
        //浏览器
        Browser browser = agent.getBrowser();

        //浏览器版本
        Version version = agent.getBrowserVersion();
        //操作系统
        OperatingSystem os = agent.getOperatingSystem();
        SysLog sysLog1 = add(admin, ip, browser, version, os);
        return sysLog1;

    }

    @Override
    //新增管理员登录日志
    public SysLog addSysLog(Person person, HttpServletRequest request) {
        //登录成功后新增一个日志
        //或许所需的信息资源
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        String agentStr = request.getHeader("user-agent");
        UserAgent agent = UserAgent.parseUserAgentString(agentStr);
        //浏览器
        Browser browser = agent.getBrowser();

        //浏览器版本
        Version version = agent.getBrowserVersion();
        //操作系统
        OperatingSystem os = agent.getOperatingSystem();
        SysLog sysLog2 = add(person, ip, browser, version, os);
        return sysLog2;
    }

    //获取日志必要的信息
    public SysLog add(Person person, String ip, Browser browser, Version version, OperatingSystem os) {

        SysLog sysLog = new SysLog();
        String str = "用户登录";
        sysLog.setLogId(sysLogMapper.getMaxLogId());
        //日志内容
        sysLog.setLogContent(str);
        sysLog.setBrowser(browser.toString() + version.toString());//浏览器名称+浏览器版本
        sysLog.setIpAddress(ip);//ip地址
        sysLog.setOs(os.toString());//操作系统
        sysLog.setUserAccount(person.getAccount());//用户账号
        sysLog.setCreatedate(person.getCreateTime());

        return sysLog;
    }


    public SysLog add(Admin admin, String ip, Browser browser, Version version, OperatingSystem os) {

        SysLog sysLog = new SysLog();
        String str = "管理员登录";
        sysLog.setLogId(sysLogMapper.getMaxLogId());
        //日志内容
        sysLog.setLogContent(str);
        sysLog.setBrowser(browser.toString() + version.toString());//浏览器名称+浏览器版本
        sysLog.setIpAddress(ip);//ip地址
        sysLog.setOs(os.toString());//操作系统
        sysLog.setUserAccount(admin.getAdminAccount());//用户账号
        sysLog.setCreatedate(admin.getCreateTime());

        return sysLog;
    }

    // 将 LocalDateTime 转换为 Date 对象
    public static Date convertToLocalDateTimeToDate(LocalDateTime dateTime) {
        return java.util.Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
