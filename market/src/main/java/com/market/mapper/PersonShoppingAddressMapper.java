package com.market.mapper;

import com.market.controller.vo.OrderCustomerVO;
import com.market.entity.PersonShoppingAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface PersonShoppingAddressMapper extends BaseMapper<PersonShoppingAddress> {

    //查找OrderCustomerVO类所需要的数据，并将其封装
    List<OrderCustomerVO> listOrderCustomerVo();

    @Select("SELECT * FROM person_shopping_address WHERE person_id = #{personId}")
    List<PersonShoppingAddress> findByPersonId(Integer personId);

    @Select("DELETE FROM person_shopping_address WHERE shopping_address_id = #{shoppingAddressId}")
    boolean deleteById(Integer shoppingAddressId);

    @Select("DELETE FROM person_shopping_address WHERE person_id = #{personId}")
    void deleteByPersonId(Integer personId);

    @Select("SELECT MAX(shopping_address_id) FROM person_shopping_address")
    Integer getMaxId();
}
