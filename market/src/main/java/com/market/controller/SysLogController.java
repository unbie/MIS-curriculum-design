package com.market.controller;


import com.market.common.Result;
import com.market.entity.SysLog;
import com.market.service.SysLogService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/sys-log")
@CrossOrigin
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    //后台日志管理展示日志信息
    @GetMapping("/listLog")
    public Result listLog(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return Result.success(sysLogService.list(), (long) sysLogService.list().size());
        } else {
            return Result.fail();
        }
    }

    //新增日志
    @PostMapping("/addLog")
    public Result addLog(@RequestBody SysLog sysLog, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return sysLogService.saveOrUpdate(sysLog) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    //删除日志
    @GetMapping("/delete")
    public Result deleteLog(@RequestParam("logId") List<Integer> logIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer logId : logIdList) {
                sysLogService.removeById(logId);
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }
}
