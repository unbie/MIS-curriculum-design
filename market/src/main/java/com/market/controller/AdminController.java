package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.market.common.Result;
import com.market.entity.Admin;
import com.market.entity.SysLog;
import com.market.service.AdminService;
import com.market.service.SysLogService;
import com.market.utils.FileUtil;
import com.market.utils.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/listA")
    public Result listA() {
        return Result.success(adminService.list(), (long) adminService.list().size());
    }


    //需要实现的功能，用户账户密码正确之后返回一个token
    @PostMapping("/adminLogin")
    public JSONObject userLogin(@RequestParam("account") String account, @RequestParam("password") String password, HttpServletRequest request) {
        Admin admin = new Admin();
        String token = "";
        String msg = "";
        JSONObject res = new JSONObject();

        //通过前端返回的account 在管理员表中检索数据 返回admin对象
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getAdminAccount, account)
                .eq(Admin::getPassword, password)
                .last("limit 1");
        Admin admin1 = adminService.getOne(wrapper);
        if (admin1 != null && admin1.getIsLocked() == 0) {
            msg = "登录成功,正在进入首页。。。";
            token = TokenUtil.createToken(admin);  // 登录成功后,创建一个token

            //新增日志
            SysLog sysLog = sysLogService.addSysLog(admin1, request);
            sysLogService.save(sysLog);
        } else if (admin1 != null && admin1.getIsLocked() == 1) {
            msg = "登录失败，该管理员账号已被封锁";
        } else {
            msg = "登录失败，请检查账号密码";
        }
        res.put("token", token);
        res.put("msg", msg);
        res.put("admin", admin1);
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

    /**
     * 管理员修改个人信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Admin admin, HttpServletRequest request) {
        String token = request.getHeader("token");
        UpdateWrapper<Admin> wrapper = new UpdateWrapper<>();
        wrapper.eq("admin_id", admin.getAdminId());
        if (TokenUtil.checkToken(token)) {
            if (!adminService.update(admin, wrapper)) {
                return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/list")
    public Result adminList(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<Admin> aa = adminService.list();
            return Result.success(aa);
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/upload")
    public Result getPictureFileName(MultipartFile file) {

        String oldFileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath();
        String newFileName = System.currentTimeMillis() + oldFileName;

        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success(newFileName);

    }

    @PostMapping("/saveOrModGood")
    public Result saveOrMod(@RequestBody Admin admin, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (!adminService.saveOrUpdate(admin)) {
                return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }


    @GetMapping("/delete")
    public Result delete(@RequestParam("adminId") Integer adminId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (!adminService.removeById(adminId))
                return Result.fail();
            return Result.success();
        } else {
            return Result.fail();
        }
    }


}
