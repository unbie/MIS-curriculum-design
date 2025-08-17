package com.market.service.impl;

import com.market.controller.dto.GoodDTO;
import com.market.controller.vo.GoodVo;
import com.market.entity.Good;
import com.market.mapper.GoodMapper;
import com.market.mapper.ImgAndGoodMapper;
import com.market.service.GoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    @Autowired
    ImgAndGoodMapper imgAndGoodMapper;

    @Autowired
    private GoodMapper goodMapper;

    public List<GoodVo> getData() {
        List<GoodVo> aa = new ArrayList<>();
        List<Good> goodList = list();
        for (int i = 0; i < goodList.size(); i++) {
            GoodVo goodVOA = new GoodVo(goodList.get(i), imgAndGoodMapper.getImgUrl(goodList.get(i).getGoodId()));
            aa.add(goodVOA);
        }
        return aa;
    }

    public List<Good> getTest() {
        List<Good> b = list();
        return b;
    }

    /**
     * 根据用户浏览量view中的数据，找到浏览量前2的分类，再通过分类id，各随机选取该分类id的四个商品
     */
    @Override
    public List<GoodDTO> getTopCategoryGoods(Integer personId) {
        return goodMapper.getTopCategoryGoods(personId);
    }

    /**
     * 根据关键字查找商品
     *
     * @param keyword
     * @return
     */
    @Override
    public List<GoodDTO> searchGoodsByKeyword(String keyword, Integer isSale) {
        // 使用关联查询的方法来搜索商品列表
        return goodMapper.searchGoodsByKeyword(keyword, isSale);
    }

    /**
     * 根据分类id找商品
     *
     * @return
     */
    @Override
    public List<GoodDTO> searchByCategory(Integer categoryId, Integer isSale) {
        List<GoodDTO> dtos = goodMapper.findByCategoryId(categoryId, isSale);
        return dtos;
    }

    /**
     * 获取求购商品
     *
     * @return
     */
    @Override
    public List<GoodDTO> getShopForGoods() {
        return goodMapper.getShopForGoods();
    }

    @Override
    public Integer getMaxId() {
        return goodMapper.getMaxId();
    }
}

