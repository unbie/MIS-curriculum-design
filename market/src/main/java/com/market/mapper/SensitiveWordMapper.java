package com.market.mapper;

import com.market.controller.vo.SensitivePartVo;
import com.market.entity.SensitiveWord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface SensitiveWordMapper extends BaseMapper<SensitiveWord> {
    //获取敏感词Vo
    List<SensitivePartVo> listSensitiveWordVo();

    //获取敏感词列表
    List<String> listSensitiveWord();
}
