package com.chenzheng.takeOut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzheng.takeOut.entity.User;
import com.chenzheng.takeOut.mapper.UserMapper;
import com.chenzheng.takeOut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String sendMsg(String phone) {
        String code = String.format("%04d", new Random().nextInt(10000)); // 生成 4 位随机数
        log.info("验证码为：{}",code);
        return code;
    }

    @Override
    public User login(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setStatus(1); // 设置状态为正常
            userMapper.insert(user);
        }
        return user;
    }
}





