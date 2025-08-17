package com.market.controller;


import com.market.common.Result;
import com.market.common.ViewsBack;
import com.market.service.ViewsService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>


 */
@RestController
@RequestMapping("/views")
public class ViewsController {

    @Autowired
    private ViewsService viewsService;

    /**
     * 点击商品，增加对应用户和对应分类id的浏览量
     */
    @PostMapping("/addView")
    public Result addView(@RequestBody ViewsBack viewsBack, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            viewsService.addView(viewsBack.getGoodId(), viewsBack.getPersonId());
            return Result.success();
        } else {
            return Result.fail();
        }
    }

}
