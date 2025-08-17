package com.market.mapper;

import com.market.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *

 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    //获取到当前日志中最大值的序号
    Integer getMaxLogId();
}
