package com.market.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.market.common.Result;
import com.market.entity.*;
import com.market.service.GoodService;
import com.market.service.LeaveMessagesService;
import com.market.service.PersonService;
import com.market.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/leave-messages")
public class LeaveMessagesController {

    @Autowired
    LeaveMessagesService leaveMessagesService;

    @Autowired
    GoodService goodService;


    @Autowired
    PersonService personService;

    @PostMapping("/saveOrModGood")
    public Result saveOrMod(@RequestBody LeaveMessages leaveMessages, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            //System.out.println(leaveMessages);
            if (!leaveMessagesService.saveOrUpdate(leaveMessages)) {
                return Result.fail();
            }
            return Result.success();
        }
        return Result.fail();

    }

    //获取某个商品的所有评论
    @GetMapping("/list")
    public Result getLeaveMessages(@RequestParam("goodId") Integer goodId, HttpServletRequest request) {
        String token = request.getHeader("token");

        if (TokenUtil.checkToken(token)) {
            //Map<String, Objects> map = new HashMap<>();
            //获取所有留言
            List<LeaveMessages> allMessages = leaveMessagesService.findById(goodId);
            //获取父留言
            List<LeaveMessages> parentMessages = allMessages.stream().filter(leaveMessage -> leaveMessage.getParentId() == null).collect(Collectors.toList());
            for (LeaveMessages lea : parentMessages) {
                lea.setChildren(allMessages.stream().filter(leaveMes -> lea.getCommentId().equals(leaveMes.getParentId())).collect(Collectors.toList()));
            }
            return Result.success(allMessages);
        }
        return Result.fail();
    }


    //通过用户id获取他的商品的所有评论
    @GetMapping("checkMes")
    public Result checkMes(@RequestParam("personId") Integer personId, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {

            //获取一个人的所有商品
            LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Good::getPublisherId, personId);
            List<Good> goodList = goodService.list(wrapper);
            List<LeaveMessages> allMessages = new ArrayList<>();
            for (Good good : goodList) {
                //获取一级评论
                List<LeaveMessages> Messages = leaveMessagesService.findById(good.getGoodId());
                List<LeaveMessages> parentMessages = Messages.stream().filter(leaveMessage -> leaveMessage.getParentId() == null).collect(Collectors.toList());

                for (LeaveMessages lea : parentMessages) {
                    //获取评论者用户名
                    lea.setReviewerName(personService.getById(lea.getReviewerId()).getUserName());
                    //添加到返回的数组中
                    allMessages.add(lea);
                }
            }

            //获取某个商品的所有评论
            System.out.println(allMessages);
            return Result.success(allMessages);
        }
        return Result.fail();
    }

    @GetMapping("getMaxId")
    public Result getMaxId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<LeaveMessages> leaveMessages = leaveMessagesService.list();
            Optional<LeaveMessages> maxCommentIdMessage = leaveMessages.stream()
                    .max(Comparator.comparingInt(LeaveMessages::getCommentId));

            // 检查是否存在具有最大commentId的LeaveMessages对象
            if (maxCommentIdMessage.isPresent()) {
                LeaveMessages message = maxCommentIdMessage.get();
                // 在此处使用具有最大commentId的LeaveMessages对象
                return Result.success(message.getCommentId());

            }
            return Result.success(0);

        } else {
            return Result.fail();
        }
    }

    @GetMapping("toGetMaxId")
    public Result toGetMaxId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (TokenUtil.checkToken(token)) {
            List<LeaveMessages> leaveMessages = leaveMessagesService.list();

            return Result.success(leaveMessages);
        } else {
            return Result.fail();
        }
    }


}