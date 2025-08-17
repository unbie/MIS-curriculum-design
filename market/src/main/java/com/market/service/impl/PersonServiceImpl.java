package com.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.market.controller.vo.PersonVo;
import com.market.entity.NearSell;
import com.market.entity.Person;
import com.market.mapper.NearSellMapper;
import com.market.mapper.PersonMapper;
import com.market.service.PersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.market.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.sql.SQLOutput;
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
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    @Resource
    private PersonMapper personMapper;
    @Resource
    private NearSellMapper nearSellMapper;

    @Override
    public List<PersonVo> listP() {
        return personMapper.listPersonVo();
    }

    @Override
    public Person saveOrUpdate(PersonVo personVo) {
        return mapPersonVoToPerson(personVo);
    }

    @Override
    public Integer getMaxId() {
        return personMapper.getMaxId() + 1;
    }

    public Person mapPersonVoToPerson(PersonVo personVo) {
        Person person = new Person();
        person.setPersonId(personVo.getPersonId());
        person.setAccount(personVo.getAccount());
        person.setUserName(personVo.getUserName());
        person.setSex(personVo.getSex());

        Person isExist = personMapper.selectById(personVo.getPersonId());
        if (isExist == null) {
            //MD5码转换
            String password = MD5Util.MD5Encode(personVo.getPassword(), "UTF-8");
            person.setPassword(password);
            person.setSex(personVo.getSex());
        }

        //campus_id
        person.setCampusId(nearSellMapper.selectIdByName(personVo.getCampusName()));

        person.setRegisterDate(personVo.getRegisterDate());
        person.setIsLocked(personVo.getIsLocked());
        person.setCreateTime(personVo.getRegisterDate());
        return person;
    }
}
