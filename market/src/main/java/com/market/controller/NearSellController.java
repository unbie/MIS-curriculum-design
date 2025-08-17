package com.market.controller;


import com.market.common.Result;
import com.market.controller.vo.CampusGoodIsConfiguredVO;
import com.market.controller.vo.CampusVO;
import com.market.entity.NearSell;
import com.market.service.NearSellService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 附近热卖配置 控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/near-sell")
public class NearSellController {

    @Autowired
    private NearSellService nearSellService;

    /**
     * 提供所有学校学校名 id号 创建时间
     *
     * @return
     */
    @GetMapping("/listCampuses")
    public Result listCampuses(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<CampusVO> campuses = nearSellService.getCampuses();
            return Result.success(campuses, (long) campuses.size());
        } else {
            return Result.fail();
        }
    }

    /**
     * 提供所有学校学校名 id号 创建时间
     *
     * @return
     */
    @GetMapping("/listCampusesWithNoToken")
    public Result listCampusesWithNoToken() {
        List<CampusVO> campuses = nearSellService.getCampuses();
        return Result.success(campuses, (long) campuses.size());
    }

    /**
     * 新增学校
     *
     * @param campus
     * @return
     */
    @PostMapping("/addCampus")
    public Result addCampus(@RequestBody NearSell campus, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (nearSellService.save(campus))
                return Result.success();
            return Result.fail();
        } else {
            return Result.fail();
        }

    }

    /**
     * 根据学校id 删除学校
     *
     * @param campusId
     * @return
     */
    @GetMapping("/deleteCampus")
    public Result deleteCampus(@RequestParam("campusId") Integer campusId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (nearSellService.removeById(campusId)) {
                return Result.success();
            }
            return Result.fail();
        } else {
            return Result.fail();
        }

    }

    /**
     * 获取全部学校已经配置的商品
     * CampusGoodIsConfiguredVO
     * {
     * campus_id: 1,
     * good_id:1,
     * good_name: '商品1',
     * good_image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg')
     * },
     */
    @GetMapping("/listCampusGoodsConfigured")
    public Result listCampusGoodsConfigured(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<CampusGoodIsConfiguredVO> campusGoodsConfigured = nearSellService.getCampusGoodsConfigured();
            return Result.success(campusGoodsConfigured, Long.valueOf(campusGoodsConfigured.size()));

        } else {
            return Result.fail();
        }
    }

    /**
     * 获取全部学校的全部商品
     * { campus_id: 1,
     * good_id:1,
     * good_name: '商品1',
     * good_image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
     * createTime:'2006-12-01 00:00:00'
     * }
     */
    @GetMapping("/listCampusGoods")
    public Result listCampusGoods(HttpServletRequest request) {
        //通过near_sell表找到学校id campusId 并存入vo对象
        //通过学校id连接person表找到person的personId 存入vo对象
        //通过personId连接good表 找到所有对应的goodId 一个个的存入vo
        //通过goodId连接good表，找到对应的goodName 存入vo
        //通过goodId连接img_and_good表，找到第一个适配的记录中的 goodImageUrl
        // 填入对应的 goodImageUrl
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<CampusGoodIsConfiguredVO> campusGoods = nearSellService.getCampusGoods();
            return Result.success(campusGoods, (long) campusGoods.size());
        } else {
            return Result.fail();
        }

    }

    @PostMapping("/updateConfigured")
    public Result updateConfigured(@RequestBody NearSell nearSell, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (nearSellService.updateById(nearSell))
                return Result.success();
            return Result.fail();
        } else {
            return Result.fail();
        }

    }

    /**
     * 保存更新之后的数据
     *
     * @param nearSell
     * @return
     */
    @PostMapping("/saveConfigured")
    public Result saveConfigured(@RequestBody NearSell nearSell, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            if (nearSellService.save(nearSell))
                return Result.success();
            return Result.fail();
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据学校id获取该学校配置的商品
     */
    @GetMapping("/getConfiguredById")
    public Result getConfiguredById(@RequestParam("campusId") Integer campusId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            return nearSellService.getConfiguredById(campusId);
        } else {
            return Result.fail();
        }
    }
}
