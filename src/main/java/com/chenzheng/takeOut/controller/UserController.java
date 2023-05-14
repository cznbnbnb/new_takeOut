package com.chenzheng.takeOut.controller;


import com.chenzheng.takeOut.common.R;
import com.chenzheng.takeOut.common.VerificationCodeService;
import com.chenzheng.takeOut.entity.User;
import com.chenzheng.takeOut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        String phone = user.getPhone();
        String code = userService.sendMsg(phone);
        verificationCodeService.setCode(phone, code);  // 将验证码保存到 service 中，而不是 session
        return R.success("验证码已发送");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> loginInfo, HttpServletRequest request) {
        String phone = loginInfo.get("phone");
        String code = loginInfo.get("code");
        String correctCode = verificationCodeService.getCode(phone);
        if (correctCode != null && correctCode.equals(code)) {
            User loggedUser = userService.login(phone);
            if (loggedUser != null) {
                //将登录的用户信息存入session
                request.getSession().setAttribute("user", loggedUser.getId());
                return R.success(loggedUser);
            } else {
                return R.error("登录失败");
            }
        } else {
            return R.error("验证码错误或已过期");
        }
    }

}


