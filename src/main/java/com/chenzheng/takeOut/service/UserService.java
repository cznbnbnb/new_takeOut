package com.chenzheng.takeOut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzheng.takeOut.entity.User;


public interface UserService extends IService<User> {
    public String sendMsg(String phone);
    public User login(String phone);
}



