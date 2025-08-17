package com.market.service;

import com.market.common.Result;
import com.market.controller.vo.CampusGoodIsConfiguredVO;
import com.market.controller.vo.CampusVO;
import com.market.entity.NearSell;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface NearSellService extends IService<NearSell> {

    List<CampusVO> getCampuses();

    List<CampusGoodIsConfiguredVO> getCampusGoodsConfigured();

    List<CampusGoodIsConfiguredVO> getCampusGoods();

    Result getConfiguredById(Integer campusId);
}
