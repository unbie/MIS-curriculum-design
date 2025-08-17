package com.market.service;

import com.market.controller.vo.SensitivePartVo;
import com.market.entity.SensitiveWord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface SensitiveWordService extends IService<SensitiveWord> {


    //新增敏感词
    SensitiveWord saveOrUpdate(SensitivePartVo sensitivePartVo);
}
