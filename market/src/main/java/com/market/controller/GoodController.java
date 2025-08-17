package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.market.common.Result;
import com.market.controller.dto.GoodDTO;
import com.market.controller.vo.GoodVo;
import com.market.controller.vo.GoodWithOneImgVO;
import com.market.entity.Good;
import com.market.entity.ImgAndGood;
import com.market.mapper.ImgAndGoodMapper;
import com.market.service.GoodService;
import com.market.service.ImgAndGoodService;
import com.market.utils.FileUtil;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>

 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    public GoodService goodService;
    @Autowired
    public ImgAndGoodService imgAndGoodService;

    @Resource
    private ImgAndGoodMapper imgAndGoodMapper;

    @GetMapping("/list")
    public Result goodList(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodVo> aa = goodService.getData();
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

    @PostMapping("/updateGoodAndImage")
    public Result updateGoodAndImage(@RequestBody GoodWithOneImgVO goodWithOneImgVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            ImgAndGood imgAndGood = new ImgAndGood();
            Good good = goodWithOneImgVO.getGood();
            imgAndGood.setImgUrl(goodWithOneImgVO.getImgUrl());
            imgAndGood.setGoodId(good.getGoodId());
            imgAndGood.setCreateTime(good.getCreateTime());
            //System.out.println(imgAndGood);
            goodService.updateById(good);
            imgAndGoodService.updateById(imgAndGood);

            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/addGoodAndImage")
    public Result addGoodAndImage(@RequestBody GoodWithOneImgVO goodWithOneImgVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            ImgAndGood imgAndGood = new ImgAndGood();

            Good good = goodWithOneImgVO.getGood();
            good.setGoodId(goodService.getMaxId() + 1);

            imgAndGood.setImgUrl(goodWithOneImgVO.getImgUrl());
            imgAndGood.setGoodId(good.getGoodId());
            imgAndGood.setCreateTime(good.getCreateTime());

            goodService.save(good);
            imgAndGoodService.save(imgAndGood);

            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/saveOrModGood")
    public Result saveOrMod(@RequestBody GoodVo goodVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            //使用VO类给实体类赋值
            Good good = new Good();

            if (goodVo.getGoodId() != 0) {
                good.setGoodId(goodVo.getGoodId());
            } else {
                good.setGoodId(goodService.getMaxId() + 1);
            }
            good.setPrice(goodVo.getPrice());
            good.setGoodName(goodVo.getGoodName());
            good.setCreateTime(goodVo.getCreateTime());
            good.setCounts(goodVo.getCounts());
            good.setIsSale(goodVo.getIsSale());
            good.setDescription(goodVo.getDescription());
            good.setExpressPrice(goodVo.getExpressPrice());
            good.setPublisherId(goodVo.getPublisherId());
            good.setIsSale(goodVo.getIsSale());
            good.setIsSale(goodVo.getIsSale());
            good.setPublisherId(goodVo.getPublisherId());

            boolean flag = true;//判断这个图片插入是否有问题
            List<String> imgUrl = goodVo.getImage();

            for (int i = 0; i < imgUrl.size(); i++) {
                ImgAndGood imgAndGood = new ImgAndGood();
                imgAndGood.setImgUrl(imgUrl.get(i));
                imgAndGood.setGoodId(goodVo.getGoodId());
                imgAndGood.setCreateTime(goodVo.getCreateTime());
                //flag = imgAndGoodService.saveOrUpdate(imgAndGood)&&flag;
                if (!imgAndGoodService.saveOrUpdate(imgAndGood))
                    return Result.fail();
            }

            if (!goodService.saveOrUpdate(good)) {
                return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam("goodId") Integer goodId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<ImgAndGood> urllist = imgAndGoodService.getList(goodId);
            for (int i = 0; i < urllist.size(); i++) {

                if (!imgAndGoodService.removeById(urllist.get(i)))

                    return Result.fail();
            }
            if (!goodService.removeById(goodId))
                return Result.fail();

            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("goodId") List<Integer> goodIdList, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            for (Integer goodId : goodIdList) {
                List<ImgAndGood> urllist = imgAndGoodService.getList(goodId);
                for (int i = 0; i < urllist.size(); i++) {
                    if (!imgAndGoodService.removeById(urllist.get(i)))
                        return Result.fail();
                }
                if (!goodService.removeById(goodId))
                    return Result.fail();
            }
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    //显示某一个用户的正在出售或者正在求购的所有商品以及对应的首张图片
    @GetMapping("/getImg")
    public Result getImg(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            //在商品表中找到personId所对应的所有商品列表
            LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Good::getPublisherId, personId);
            List<Good> goodList = goodService.list(wrapper);

            List<GoodWithOneImgVO> goodWithOneImgVOList = new ArrayList<>();

            for (Good good : goodList) {
                GoodWithOneImgVO goodWithOneImgVO = new GoodWithOneImgVO();
                // 根据goodId连接img_and_good表找到第一个适配的记录中的goodImageUrl
                ImgAndGood imgAndGood = imgAndGoodMapper.selectOne(new QueryWrapper<ImgAndGood>().eq("good_id", good.getGoodId()).last("limit 1"));
//              ImgAndGood imgAndGood = imgAndGoodMapper.selectByGoodId(goodId);
                goodWithOneImgVO.setGood(good);
                if (!imgAndGood.getImgUrl().isEmpty()) {
                    goodWithOneImgVO.setImgUrl(imgAndGood.getImgUrl());
                }
                goodWithOneImgVOList.add(goodWithOneImgVO);
            }

            return Result.success(goodWithOneImgVOList, (long) goodWithOneImgVOList.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据用户浏览量view中的数据，找到浏览量前2的分类，再通过分类id，各随机选取该分类id的四个商品
     */
    @GetMapping("/getByCategoryIds")
    public Result getByCategoryIds(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodDTO> goods = goodService.getTopCategoryGoods(personId);
            return Result.success(goods);
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据搜索关键词，返回相关商品
     */
    @GetMapping("/search")
    public Result searchGoods(@RequestParam("keyword") String keyword, @RequestParam("isSale") Integer isSale, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodDTO> goodDTOS = goodService.searchGoodsByKeyword(keyword, isSale);
            return Result.success(goodDTOS, (long) goodDTOS.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据商品分类id返回相关商品
     */
    @GetMapping("/searchByCategory")
    public Result searchByCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("isSale") Integer isSale, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodDTO> goodDTOS = goodService.searchByCategory(categoryId, isSale);
            return Result.success(goodDTOS, (long) goodDTOS.size());
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/getAGood")
    public Result selectById(@RequestParam("goodId") Integer goodId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodVo> aa = goodService.getData();
            for (GoodVo a : aa) {
                if (a.getGoodId() == goodId) {
                    return Result.success(a);
                }

            }
            return Result.fail();
        }
        return Result.fail();

    }

    /**
     * 获取is_sale字段为0的所有商品返回goodDTO的list
     */
    @GetMapping("/loadShopForGoods")
    public Result loadShopForGoods(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<GoodDTO> shopForGoods = goodService.getShopForGoods();
            return Result.success(shopForGoods, (long) shopForGoods.size());
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/buy")
    public Result buyGoods(@RequestParam("goodId") Integer goodId, @RequestParam("count") Integer count, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            Good a = goodService.getById(goodId);
            a.setCounts(a.getCounts() - count);
            if (!goodService.saveOrUpdate(a))
                return Result.fail();
            return Result.success();
        } else {
            return Result.fail();
        }
    }


}
