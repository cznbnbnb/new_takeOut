package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.common.BaseContext;
import com.chenzheng.takeOut.entity.*;
import com.chenzheng.takeOut.mapper.OrderMapper;
import com.chenzheng.takeOut.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Override
    public Page<Orders> getUserOrdersPage(int page, int pageSize) {
        // 获取当前登录的用户ID
        Long userId = BaseContext.getCurrentId();

        // 使用MyBatis-Plus的分页查询
        Page<Orders> orderPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);
        return this.page(orderPage, queryWrapper);
    }


}