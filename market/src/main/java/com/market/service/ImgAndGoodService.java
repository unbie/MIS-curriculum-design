package com.market.service;

import com.market.entity.ImgAndGood;
import com.baomidou.mybatisplus.extension.service.IService;
import com.market.mapper.ImgAndGoodMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *

 */
public interface ImgAndGoodService extends IService<ImgAndGood> {


    public List<ImgAndGood> getList(Integer goodId);
}
