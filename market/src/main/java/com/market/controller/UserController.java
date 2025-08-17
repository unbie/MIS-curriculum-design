package com.market.controller;

import com.market.entity.Person;
import com.market.mapper.PersonMapper;
import com.market.utils.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.channels.SeekableByteChannel;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

 **/
@RestController
public class UserController {

    private HttpSession session;

    // 用户登录验证
    //需要实现的功能，用户账户密码正确之后返回一个token
    @PostMapping("/userLogin")
    public JSONObject userLogin(@RequestParam("account") String account, @RequestParam("password") String password) {
        Person person = new Person();
        String token = "";
        String msg = "";
        JSONObject res = new JSONObject();

        //账号验证
        if (account.equals("admin")) {
            person.setAccount(account);
            person.setPassword(password);
            person.setCreateTime(convertToLocalDateTimeToDate(LocalDateTime.now()));
            person.setRegisterDate(convertToLocalDateTimeToDate(LocalDateTime.now()));
            //密码验证

            if (person.getPassword().equals("123")) {
                msg = "登录成功,正在进入首页。。。";
                token = TokenUtil.createToken(person);  // 登录成功后,创建一个token
                res.put("user", person);

            } else {
                msg = "登录失败，请检查密码";
            }
        } else {
            msg = "登录失败，请检查账号密码";
        }

        res.put("token", token);
        res.put("msg", msg);
        return res;
    }

    // 检验token
    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return TokenUtil.checkToken(token);
    }

    // 将 LocalDateTime 转换为 Date 对象
    public static Date convertToLocalDateTimeToDate(LocalDateTime dateTime) {
        return java.util.Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
