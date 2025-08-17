package com.market.service.impl;

import com.market.controller.vo.SensitivePartVo;
import com.market.entity.SensitiveWord;
import com.market.mapper.SensitiveWordMapper;
import com.market.service.SensitiveWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper, SensitiveWord> implements SensitiveWordService {

    @Override
    public SensitiveWord saveOrUpdate(SensitivePartVo sensitivePartVo) {
        SensitiveWord sensitiveWord = new SensitiveWord();
        //进行转化
        sensitiveWord.setSensitiveId(sensitivePartVo.getSensitiveId());
        sensitiveWord.setWord(sensitivePartVo.getWord());
        sensitiveWord.setCreateTime(sensitivePartVo.getCreateTime());

        return sensitiveWord;
    }
}
