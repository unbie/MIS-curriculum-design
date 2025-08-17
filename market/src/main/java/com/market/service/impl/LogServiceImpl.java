package com.market.service.impl;

import com.market.entity.Log;
import com.market.mapper.LogMapper;
import com.market.service.LogService;
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
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
