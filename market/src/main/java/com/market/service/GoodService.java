package com.market.service;

import com.market.controller.dto.GoodDTO;
import com.market.controller.vo.GoodVo;
import com.market.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface GoodService extends IService<Good> {


    public List<GoodVo> getData();

    public List<Good> getTest();


    List<GoodDTO> getTopCategoryGoods(Integer personId);

    /**
     * 根据关键词搜索商品
     *
     * @param keyword
     * @return
     */
    List<GoodDTO> searchGoodsByKeyword(String keyword, Integer isSale);

    List<GoodDTO> searchByCategory(Integer categoryId, Integer isSale);

    List<GoodDTO> getShopForGoods();

    Integer getMaxId();
}
