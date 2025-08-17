package com.market.service;

import com.market.entity.PersonShoppingAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface PersonShoppingAddressService extends IService<PersonShoppingAddress> {

    // 获取指定 personId 的所有地址
    public List<PersonShoppingAddress> getByPersonId(Integer personId);

    // 根据指定的 shoppingAddressId 删除地址
    public void deleteById(Integer shoppingAddressId);

    public void deleteAddressByPersonId(Integer personId);

    // 插入前端获取到的list
    public void add(List<PersonShoppingAddress> personShoppingAddressList);
}
