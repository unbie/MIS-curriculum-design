package com.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.market.common.Result;
import com.market.controller.vo.CampusGoodIsConfiguredVO;
import com.market.controller.vo.CampusVO;
import com.market.controller.vo.GoodWithImgVO;
import com.market.entity.Good;
import com.market.entity.ImgAndGood;
import com.market.entity.NearSell;
import com.market.entity.Person;
import com.market.mapper.GoodMapper;
import com.market.mapper.ImgAndGoodMapper;
import com.market.mapper.NearSellMapper;
import com.market.mapper.PersonMapper;
import com.market.service.NearSellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guyue
 * @since 2023-06-27
 */
@Service
public class NearSellServiceImpl extends ServiceImpl<NearSellMapper, NearSell> implements NearSellService {

    @Autowired
    private NearSellMapper nearSellMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private ImgAndGoodMapper imgAndGoodMapper;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<CampusVO> getCampuses() {
        QueryWrapper<NearSell> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("campus_id", "campus_name", "create_time");
        List<NearSell> nearSellList = nearSellMapper.selectList(queryWrapper);

        List<CampusVO> campusVOList = new ArrayList<>();
        for (NearSell nearSell : nearSellList) {
            CampusVO campusVO = new CampusVO();
            BeanUtils.copyProperties(nearSell, campusVO);
            campusVOList.add(campusVO);
        }
        return campusVOList;
    }

    /**
     * 获取学校对应的已配置的商品包括学校id、商品id、name、url、createTime
     *
     * @return
     */
    @Override
    public List<CampusGoodIsConfiguredVO> getCampusGoodsConfigured() {
        // 获取 NearSell 表中的所有数据
        List<NearSell> nearSells = nearSellMapper.selectList(null);
        List<CampusGoodIsConfiguredVO> campusGoods = new ArrayList<>();

        for (NearSell nearSell : nearSells) {
            // 依次遍历九个商品字段
            List<Integer> goodIds = Arrays.asList(
                    nearSell.getGoodId1(), nearSell.getGoodId2(), nearSell.getGoodId3(),
                    nearSell.getGoodId4(), nearSell.getGoodId5(), nearSell.getGoodId6(),
                    nearSell.getGoodId7(), nearSell.getGoodId8()
            );

            for (Integer goodId : goodIds) {
                // 判断 goodId 是否为空，为空则舍去
                if (goodId != null) {
                    // 填入 VO 的 campusId 和 goodId
                    CampusGoodIsConfiguredVO campusGoodItem = new CampusGoodIsConfiguredVO();
                    campusGoodItem.setCampusId(nearSell.getCampusId());
                    campusGoodItem.setGoodId(goodId);

                    // 通过 goodId 找到对应的 goodName
                    Good good = goodMapper.selectById(goodId);
                    if (good != null) {
                        // 填入对应的 goodName和createTime
                        campusGoodItem.setGoodName(good.getGoodName());
                        campusGoodItem.setCreateTime(good.getCreateTime());

                        // 通过 goodId 找到第一个适配的记录中的 goodImageUrl
                        ImgAndGood imgAndGood = imgAndGoodMapper.selectByGoodId(goodId);
                        if (imgAndGood != null) {
                            // 填入对应的 goodImageUrl
                            campusGoodItem.setGoodImageUrl(imgAndGood.getImgUrl());
                        }

                        campusGoods.add(campusGoodItem);
                    }
                }
            }
        }
        return campusGoods;
    }

    /**
     * 获取学校所有的商品包括学校id、商品id、name、url、createTime
     * @return
     */
    /**
     * 获取学校所有的商品包括学校id、商品id、name、url、createTime
     *
     * @return
     */
    @Override
    public List<CampusGoodIsConfiguredVO> getCampusGoods() {
        // 结果集
        List<CampusGoodIsConfiguredVO> resultList = new ArrayList<>();

        // 查询nearSell表中的数据
        List<NearSell> nearSellList = nearSellMapper.selectList(null);

        for (NearSell nearSell : nearSellList) {
            Integer campusId = nearSell.getCampusId();
            CampusGoodIsConfiguredVO vo = new CampusGoodIsConfiguredVO();
            vo.setCampusId(campusId);

            // 根据campusId连接person表找到personId
            List<Person> people = personMapper.selectList(new QueryWrapper<Person>().eq("campus_id", campusId));
            for (Person person : people) {
                if (person != null) {
                    Integer personId = person.getPersonId();

                    // 根据personId连接good表找到对应的goodId
                    List<Integer> goodIdList = goodMapper.selectGoodIdsByPersonId(personId);
                    if (!goodIdList.isEmpty()) {
                        // 遍历goodIdList，填充入vo中的goodId、goodName和goodImageUrl
                        for (Integer goodId : goodIdList) {
                            Good good = goodMapper.selectOne(new QueryWrapper<Good>()
                                    .eq("good_id", goodId)
                                    .eq("is_sale", 1));

                            if (good != null) {
                                CampusGoodIsConfiguredVO voCopy = new CampusGoodIsConfiguredVO();
                                voCopy.setCampusId(vo.getCampusId());
                                voCopy.setGoodId(goodId);
                                voCopy.setGoodName(good.getGoodName());
                                voCopy.setCreateTime(good.getCreateTime());

                                // 根据goodId连接img_and_good表找到第一个适配的记录中的goodImageUrl
                                List<ImgAndGood> imgAndGoods = imgAndGoodMapper.selectList(new QueryWrapper<ImgAndGood>()
                                        .eq("good_id", goodId));

                                if (imgAndGoods.get(0) != null) {
                                    voCopy.setGoodImageUrl(imgAndGoods.get(0).getImgUrl());
                                }

                                resultList.add(voCopy);
                            }
                        }
                    }
                }
            }
        }

        return resultList;
    }


    /**
     * 根据学校id获取附近热卖商品
     *
     * @param campusId
     * @return
     */
    @Override
    public Result getConfiguredById(Integer campusId) {
        NearSell nearSell = nearSellMapper.selectById(campusId);
        if (nearSell != null) {
            List<Integer> goodIds = new ArrayList<>();
            goodIds.add(nearSell.getGoodId1());
            goodIds.add(nearSell.getGoodId2());
            goodIds.add(nearSell.getGoodId3());
            goodIds.add(nearSell.getGoodId4());
            goodIds.add(nearSell.getGoodId5());
            goodIds.add(nearSell.getGoodId6());
            goodIds.add(nearSell.getGoodId7());
            goodIds.add(nearSell.getGoodId8());

            List<Good> goods = goodMapper.selectBatchIds(goodIds);
            List<ImgAndGood> imgAndGoods = imgAndGoodMapper.getImgAndGoodByGoodIds(goodIds);

            // 将商品和对应的图片进行关联
            Map<Integer, String> imgAndGoodMap = new HashMap<>();
            for (ImgAndGood imgAndGood : imgAndGoods) {
                imgAndGoodMap.put(imgAndGood.getGoodId(), imgAndGood.getImgUrl());
            }

            List<Map<String, Object>> result = new ArrayList<>();
            for (Good good : goods) {
                Integer goodId = good.getGoodId();
                if (imgAndGoodMap.containsKey(goodId)) {
                    String imgUrl = imgAndGoodMap.get(goodId);
                    Map<String, Object> item = new HashMap<>();
                    item.put("goodId", good.getGoodId());
                    item.put("goodName", good.getGoodName());
                    item.put("price", good.getPrice());
                    item.put("imgUrl", imgUrl);
                    result.add(item);
                }
            }

            return Result.success(result);
        }
        return Result.fail();
    }
}