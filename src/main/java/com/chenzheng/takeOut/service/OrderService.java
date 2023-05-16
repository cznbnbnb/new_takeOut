package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.entity.Orders;

public interface OrderService extends IService<Orders> {
    Page<Orders> getUserOrdersPage(int page, int pageSize);

}
