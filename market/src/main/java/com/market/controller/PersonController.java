package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.market.common.Result;
import com.market.controller.vo.PersonVo;
import com.market.entity.Person;
import com.market.entity.SysLog;
import com.market.service.PersonService;
import com.market.service.SysLogService;
import com.market.utils.CaptchaUtil;
import com.market.utils.MD5Util;
import com.market.utils.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private SysLogService sysLogService;

    //测试
    @GetMapping("/list")
    public List<Person> list() {
        return personService.list();
    }

    //初始页面展示数据
    @GetMapping("/listP")
    public Result listP(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<PersonVo> personVoList = personService.listP();
            return Result.success(personVoList, (long) personVoList.size());
        } else {
            return Result.fail();
        }
    }

    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam("personId") List<Integer> personIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer personId : personIdList) {
                personService.removeById(personId);
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    //用户端新增和更新用户时返回的是一个person类，管理员端新增和更新用户时返回的是一个personVO类，所以二者分开写
    //管理员端新增
    @PostMapping("/add")
    public Result add(@RequestBody PersonVo personVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return personService.save(personService.saveOrUpdate(personVo)) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    //管理员端更新
    @PostMapping("/update")
    public Result update(@RequestBody PersonVo personVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return personService.updateById(personService.saveOrUpdate(personVo)) ? Result.success() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    //用户端新增或者更新
    @PostMapping("/addOrUpdate")
    public JSONObject addOrUpdate(@RequestBody Person person, HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject res = new JSONObject();
        String msg = "";
        Person person1 = new Person();
        Integer resultCode = 0;
        if (TokenUtil.checkToken(token)) {
            //MD5码转换
            person.setPassword(MD5Util.MD5Encode(person.getPassword(), "UTF-8"));
            personService.saveOrUpdate(person);
            person1 = personService.getById(person.getPersonId());
            msg = "操作成功！";
            resultCode = 200;
        } else {
            msg = "操作失败！";
            resultCode = 400;
        }
        res.put("token", token);
        res.put("msg", msg);
        res.put("resultCode", resultCode);
        res.put("person", person1);
        return res;
    }

    //用户登录功能实现
    //带有拦截器性质
    @PostMapping("/personLogin")
    public JSONObject personLogin(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request) {
        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();

        String token = "";
        String msg = "";
        JSONObject res = new JSONObject();
//          将输入的密码转换为MD5码
        password = MD5Util.MD5Encode(password, "UTF-8");

        wrapper.eq(Person::getAccount, account)
                .eq(Person::getPassword, password)
                .last("limit 1");
        Person personInfo = personService.getOne(wrapper);

        if (personInfo != null && personInfo.getIsLocked() == 0) {
            msg = "登录成功,正在进入首页。。。";
            token = TokenUtil.createToken(personInfo);  // 登录成功后,创建一个token

            //新增日志
            SysLog sysLog = sysLogService.addSysLog(personInfo, request);
            sysLogService.save(sysLog);
        } else if (personInfo != null && personInfo.getIsLocked() == 1) {
            msg = "登录失败，该账号已被封锁，请联系管理员";
        } else {
            msg = "登录失败，请检查账号密码";
        }
        res.put("token", token);
        res.put("msg", msg);
        res.put("person", personInfo);
        return res;
    }

    @PostMapping("/personRegister")
    public JSONObject personRegister(@RequestBody PersonVo personVo, HttpServletRequest request) {
        JSONObject res = new JSONObject();
        String msg = "";
        String token = "";

        // 检查账号是否已存在
        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Person::getAccount, personVo.getAccount()).last("limit 1");
        Person existingPerson = personService.getOne(wrapper);
        if (existingPerson != null) {
            msg = "账号已存在";
            res.put("msg", msg);
            return res;
        }

        // 密码强度检查等其他验证逻辑

        // 创建新用户对象并设置信息
        // 设置其他默认属性
//        newPerson.setIsLocked(0);
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化时间
        String formattedTime = currentTime.format(formatter);
        // 将格式化后的时间字符串转换回LocalDateTime类型
        LocalDateTime parsedTime = LocalDateTime.parse(formattedTime, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = parsedTime.atZone(zoneId).toInstant();
        Date date = Date.from(instant);
        personVo.setCreateTime(date);
        personVo.setRegisterDate(date);


        Person newPerson = personService.saveOrUpdate(personVo);

        //设置一些默认属性
        newPerson.setPersonId(personService.getMaxId());
        newPerson.setIsLocked(0);

        // 调用服务层或业务逻辑层进行注册
        boolean registerResult = personService.save(newPerson);

        if (registerResult) {
            token = TokenUtil.createToken(newPerson);
            msg = "注册成功";

            //写进日志
            SysLog sysLog = sysLogService.addSysLog(newPerson, request);
            sysLogService.save(sysLog);
        } else {
            msg = "注册失败";
        }
        res.put("msg", msg);
        res.put("token", token);
        res.put("person", newPerson);

        return res;
    }

    @GetMapping("/getAPerson")
    public Result selectById(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            Person a = personService.getById(personId);
            return Result.success(a);
        }
        return Result.fail();

    }

}
