package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.market.common.Result;
import com.market.controller.vo.GoodWithOneImgVO;
import com.market.controller.vo.OrderVo;
import com.market.entity.Good;
import com.market.entity.ImgAndGood;
import com.market.entity.Shoppingcart;
import com.market.mapper.ImgAndGoodMapper;
import com.market.service.GoodService;
import com.market.service.ShoppingcartService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/shoppingcart")
public class ShoppingcartController {

    @Autowired
    ShoppingcartService shoppingcartService;

    @Autowired
    GoodService goodService;
    @Resource
    private ImgAndGoodMapper imgAndGoodMapper;

    @GetMapping("list")
    public Result getCart(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {

            LambdaQueryWrapper<Shoppingcart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Shoppingcart::getCustomerId, personId);
            List<Shoppingcart> ShoppingcartList = shoppingcartService.list(wrapper);

            for (Shoppingcart sc : ShoppingcartList) {

                GoodWithOneImgVO goodWithOneImgVO = new GoodWithOneImgVO();
                // 根据goodId连接img_and_good表找到第一个适配的记录中的goodImageUrl
                Good good = goodService.getById(sc.getGoodId());
                goodWithOneImgVO.setGood(good);
                ImgAndGood imgAndGood = imgAndGoodMapper.selectOne(new QueryWrapper<ImgAndGood>().eq("good_id", sc.getGoodId()).last("limit 1"));
                //添加图片
                if (imgAndGood != null && !imgAndGood.getImgUrl().isEmpty()) {
                    goodWithOneImgVO.setImgUrl(imgAndGood.getImgUrl());
                }
                //把这个商品vo类加入
                sc.setGood(goodWithOneImgVO);

            }
            return Result.success(ShoppingcartList);
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/delete")
    public Result deleteGood(@RequestParam("cartId") Integer cartId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (shoppingcartService.removeById(cartId)) {
                return Result.success();
            }
            return Result.fail();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("cartIds") List<Integer> cartIds, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer cartId : cartIds) {
                shoppingcartService.removeById(cartId);
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/add")
    public Result addGoods(@RequestBody Shoppingcart shoppingcart, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (!shoppingcartService.saveOrUpdate(shoppingcart)) {
                return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("getMaxId")
    public Result getMaxId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<Shoppingcart> sc = shoppingcartService.list();
            Integer a = sc.size();
            return Result.success(a);
        } else {
            return Result.fail();
        }
    }
}
