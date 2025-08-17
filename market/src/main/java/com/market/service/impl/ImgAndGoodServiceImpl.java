package com.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.market.entity.ImgAndGood;
import com.market.mapper.ImgAndGoodMapper;
import com.market.service.ImgAndGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ImgAndGoodServiceImpl extends ServiceImpl<ImgAndGoodMapper, ImgAndGood> implements ImgAndGoodService {
    @Autowired
    ImgAndGoodMapper imgAndGoodMapper;

    @Override
    public List<ImgAndGood> getList(Integer goodId) {
        return imgAndGoodMapper.selectList(new QueryWrapper<ImgAndGood>().eq("good_id", goodId));
    }
}
