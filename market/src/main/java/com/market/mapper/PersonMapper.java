package com.market.mapper;

import com.market.controller.vo.PersonVo;
import com.market.entity.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {


    //获取personVo列表
    List<PersonVo> listPersonVo();

    //获取personId的最大值
    Integer getMaxId();

    @Select("SELECT person_id FROM person WHERE account = #{account}")
    Integer findPersonIdByAccount(@Param("account") String account);
}
