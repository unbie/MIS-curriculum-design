package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.market.common.Result;
import com.market.entity.Person;
import com.market.entity.PersonShoppingAddress;
import com.market.service.PersonShoppingAddressService;
import com.market.utils.MD5Util;
import com.market.utils.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/person-shopping-address")
public class PersonShoppingAddressController {

    @Autowired
    private PersonShoppingAddressService personShoppingAddressService;

    @GetMapping("/getAddressesById")
    public Result getAddressesById(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            LambdaQueryWrapper<PersonShoppingAddress> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PersonShoppingAddress::getPersonId, personId);
            List<PersonShoppingAddress> personShoppingAddressList = personShoppingAddressService.list(wrapper);
            return Result.success(personShoppingAddressList, (long) personShoppingAddressList.size());
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam("shoppingAddressId") Integer shoppingAddressId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return personShoppingAddressService.removeById(shoppingAddressId) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    //用户端新增或者更新
    @PostMapping("/addOrUpdate")
    public JSONObject addOrUpdate(@RequestBody List<PersonShoppingAddress> personShoppingAddressList, HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject res = new JSONObject();
        String msg = "";
        Integer resultCode = 0;

        if (TokenUtil.checkToken(token)) {
            // 删除通过personId所有找到的地址
            personShoppingAddressService.deleteAddressByPersonId(personShoppingAddressList.get(0).getPersonId());
            // 获取指定 personId 的所有地址
            List<PersonShoppingAddress> existingAddresses = personShoppingAddressService.getByPersonId(personShoppingAddressList.get(0).getPersonId());
            // 将前端返回的数据插入到数据表中
            // 如果前端返回的数据中有新增数据 则该数据的shoppingAddressId为null
            personShoppingAddressService.add(personShoppingAddressList);

            msg = "操作成功！";
            resultCode = 200;
        } else {
            msg = "操作失败！";
            resultCode = 400;
        }

        res.put("token", token);
        res.put("msg", msg);
        res.put("resultCode", resultCode);
        return res;
    }
}