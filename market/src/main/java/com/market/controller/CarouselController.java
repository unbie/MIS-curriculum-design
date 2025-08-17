package com.market.controller;


import com.market.common.Result;
import com.market.entity.Carousel;
import com.market.service.CarouselService;
import com.market.utils.FileUtil;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 后台轮播图配置 控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/carousel")
@CrossOrigin
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 查所有
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<Carousel> carouselList = carouselService.list();
            return Result.success(carouselList, (long) carouselList.size());
        } else {
            return Result.fail();
        }

    }

    /**
     * 轮播图新增或修改的保存
     *
     * @param carousel
     * @param request
     * @return
     */
    @PostMapping("/saveOrModCarousel")
    public Result saveOrMod(@RequestBody Carousel carousel, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (!carouselService.saveOrUpdate(carousel)) {
                return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }

    }

    /**
     * 轮播图删除
     *
     * @param carouselIds
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam("carouselIds") List<Integer> carouselIds, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer carouselId : carouselIds) {
                carouselService.removeById(carouselId);
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    /**
     * 轮播图图片上传
     *
     * @param file
     * @return
     */
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
}
