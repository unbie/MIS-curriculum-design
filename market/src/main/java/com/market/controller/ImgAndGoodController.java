package com.market.controller;


import com.market.common.Result;
import com.market.entity.Good;
import com.market.entity.ImgAndGood;
import com.market.service.GoodService;
import com.market.service.ImgAndGoodService;
import com.market.utils.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/img-and-good")
public class ImgAndGoodController {
    @Autowired
    private ImgAndGoodService imgAndGoodService;

    @Autowired
    private GoodService goodService;

    @PostMapping("/updateGoodAndImg")
    public JSONObject updateGoodAndImg(@RequestBody List<ImgAndGood> imgAndGoods, HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject res = new JSONObject();
        String msg = "";
        Integer resultCode = 0;
        if (TokenUtil.checkToken(token)) {
            for (ImgAndGood imgAndGood : imgAndGoods) {
                imgAndGood.setGoodId(goodService.getMaxId());
                imgAndGoodService.save(imgAndGood);
            }
            msg = "操作成功！";
            resultCode = 200;
        } else {
            msg = "操作失败";
            resultCode = 400;
        }
        res.put("msg", msg);
        res.put("resultCode", resultCode);
        return res;
    }

}
