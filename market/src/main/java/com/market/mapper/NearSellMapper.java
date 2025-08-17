package com.market.mapper;

import com.market.entity.NearSell;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
@Mapper
public interface NearSellMapper extends BaseMapper<NearSell> {
    //获取对应的campus_id
    Integer selectIdByName(String campusName);
}
