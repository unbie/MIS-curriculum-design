package com.market.controller;


import com.market.common.Result;
import com.market.controller.vo.GoodVo;
import com.market.entity.GoodCategory;
import com.market.entity.ImgAndGood;
import com.market.service.GoodCategoryService;
import com.market.service.GoodService;
import com.market.utils.FileUtil;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/good-category")
public class GoodCategoryController {

    @Autowired
    GoodCategoryService goodCategoryService;

    @GetMapping("/list")
    public Result goodList(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodCategory> aa = goodCategoryService.list();
            return Result.success(aa);
        }
        return Result.fail();

    }

    @PostMapping("/upload")
    public Result getPictureFileName(MultipartFile file, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            String oldFileName = file.getOriginalFilename();
            String filePath = FileUtil.getUpLoadFilePath();
            // 选择性删除System.currentTimeMillis()代码 保证每张图片不被上传两次
            String newFileName = System.currentTimeMillis() + oldFileName;
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Result.success(newFileName);
        } else {
            return Result.fail();
        }

    }


    @PostMapping("/saveOrModGood")
    public Result saveOrMod(@RequestBody GoodCategory goodCategory, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (!goodCategoryService.saveOrUpdate(goodCategory)) {
                return Result.fail();
            }
            return Result.success();
        }
        return Result.fail();

    }

    @GetMapping("/delete")
    public Result delete(@RequestParam("goodId") List<Integer> goodIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer goodId : goodIdList) {
                if (!goodCategoryService.removeById(goodId))
                    return Result.fail();
            }
            return Result.success();
        }
        return Result.fail();

    }

    @GetMapping("/clist")
    public Result allCategory(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodCategory> a = goodCategoryService.list();
            return Result.success(a, (long) a.size());
        }
        return Result.fail();
    }


}
