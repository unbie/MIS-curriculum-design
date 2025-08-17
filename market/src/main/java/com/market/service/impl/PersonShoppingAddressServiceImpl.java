package com.market.service.impl;

import com.market.entity.PersonShoppingAddress;
import com.market.mapper.PersonShoppingAddressMapper;
import com.market.service.PersonShoppingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class PersonShoppingAddressServiceImpl extends ServiceImpl<PersonShoppingAddressMapper, PersonShoppingAddress> implements PersonShoppingAddressService {

    @Resource
    private PersonShoppingAddressMapper personShoppingAddressMapper;

    @Override
    // 获取指定 personId 的所有地址
    public List<PersonShoppingAddress> getByPersonId(Integer personId) {
        return personShoppingAddressMapper.findByPersonId(personId);
    }

    @Override
    // 根据指定的 shoppingAddressId 删除地址
    public void deleteById(Integer shoppingAddressId) {
        personShoppingAddressMapper.deleteById(shoppingAddressId);
    }


    // 根据personId删除地址
    @Override
    public void deleteAddressByPersonId(Integer personId) {
        personShoppingAddressMapper.deleteByPersonId(personId);
    }

    @Override
    public void add(List<PersonShoppingAddress> personShoppingAddressList) {

        for (PersonShoppingAddress personShoppingAddress : personShoppingAddressList) {
            Integer maxShoppingAddressId = personShoppingAddressMapper.getMaxId();
            Integer newShoppingAddressId = maxShoppingAddressId != null ? maxShoppingAddressId + 1 : 1;
            if (personShoppingAddress.getShoppingAddressId() == null) {
                personShoppingAddress.setShoppingAddressId(newShoppingAddressId);
            }
            personShoppingAddressMapper.insert(personShoppingAddress);
        }
    }

}
