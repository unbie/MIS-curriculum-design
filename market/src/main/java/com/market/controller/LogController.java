package com.market.controller;


import com.market.common.Result;
import com.market.entity.Log;
import com.market.service.LogService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/log")
@CrossOrigin
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/listLog")
    public Result listLog(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return Result.success(logService.list(), (long) logService.list().size());
        } else {
            return Result.fail();
        }
    }
}
