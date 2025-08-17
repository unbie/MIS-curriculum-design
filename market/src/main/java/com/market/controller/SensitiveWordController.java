package com.market.controller;


import com.market.common.Result;
import com.market.controller.vo.SensitivePartVo;
import com.market.entity.SensitiveWord;
import com.market.service.SensitiveWordService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/sensitive-word")
public class SensitiveWordController {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    //删除敏感词
    @GetMapping("/deleteSensitive")
    public Result deleteSensitive(@RequestParam("sensitiveId") Integer sensitiveId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return sensitiveWordService.removeById(sensitiveId) ? Result.success() : Result.fail();

        } else {
            return Result.fail();
        }
    }

    //新增敏感词
    @PostMapping("/add")
    public Result add(@RequestBody SensitivePartVo sensitivePartVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return sensitiveWordService.save(sensitiveWordService.saveOrUpdate(sensitivePartVo)) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }
}
