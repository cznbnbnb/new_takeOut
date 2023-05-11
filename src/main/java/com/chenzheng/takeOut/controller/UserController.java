package com.chenzheng.takeOut.controller;

import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.entity.User;
import com.chenzheng.takeOut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        String code = userService.sendMsg(phone);
        session.setAttribute(phone, code);
        session.setMaxInactiveInterval(60); // 设置 session 过期时间为 60 秒
        return R.success("验证码已发送");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String phone = loginInfo.get("phone");
        String code = loginInfo.get("code");
        String correctCode = (String) session.getAttribute(phone);
        if (correctCode != null && correctCode.equals(code)) {
            User loggedUser = userService.login(phone);
            if (loggedUser != null) {
                session.setAttribute("employee", loggedUser.getId());
                return R.success(loggedUser);
            } else {
                return R.error("登录失败");
            }
        } else {
            return R.error("验证码错误或已过期");
        }
    }

}


