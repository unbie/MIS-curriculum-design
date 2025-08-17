package com.market.service;

import com.market.entity.Views;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface ViewsService extends IService<Views> {

    void addView(Integer goodId, Integer personId);
}
