package com.dolphin.wechatserver.controller;

import com.dolphin.wechatserver.bean.User;
import com.dolphin.wechatserver.response.ResponseData;
import com.dolphin.wechatserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户 controller
 *
 * @author dolphin
 * @date 2025-01-21
 */
@RestController
@RequestMapping("/wechat/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 微信注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Map<Object, Object> registerWeChatUser(@RequestBody User user) {
        return userService.registerWeChatUser(user).responseData;
    }

    /**
     * 根据 id 获取用户数据
     *
     * @param userID
     * @return
     */
    @GetMapping("/getUserInfo")
    public Map<Object, Object> getUserInfo(@RequestParam("userID") Integer userID) {
        return userService.getUserInfoByID(userID).responseData;
    }

    /**
     * 根据账号密码登录
     *
     * @param requestParams
     * @return
     */
    @PostMapping("/loginByAccountAndPassword")
    public Map<Object, Object> loginByAccountAndPassword(@RequestBody Map<String, String> requestParams) {
        return userService.loginByAccountAndPassword(requestParams).responseData;
    }

    /**
     * 手机号+验证码登录（验证码使用华为 AGC 平台校验）
     *
     * @param phoneNumber
     * @return
     */
    @PostMapping("/loginByPhoneAndVerifyCode")
    public Map<Object, Object> loginByPhoneAndVerifyCode(@RequestBody String phoneNumber) {
        return userService.loginByPhoneAndVerifyCode(phoneNumber).responseData;
    }

    /**
     * 手机号 + 密码登录
     *
     * @param requestParams
     * @return
     */
    @PostMapping("/loginByPhoneAndPassword")
    public Map<Object, Object> loginByPhoneNumberAndPassword(@RequestBody Map<String, String> requestParams) {
        return userService.loginByPhoneNumberAndPassword(requestParams).responseData;
    }
}
