package com.chenzheng.takeOut.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.Orders;
import com.chenzheng.takeOut.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单显示
     * @param page
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String id){
        log.info("page = {},pageSize = {},id = {}",page,pageSize,id);
        Page<Orders> pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(id),Orders::getId,id);
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}