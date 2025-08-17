package com.market.service;

import com.market.controller.vo.PersonVo;
import com.market.entity.Person;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface PersonService extends IService<Person> {
    //用户管理初始页面数据
    List<PersonVo> listP();

    //新增或更新用户
    Person saveOrUpdate(PersonVo personVo);

    //获取当前最大的id
    Integer getMaxId();
}
