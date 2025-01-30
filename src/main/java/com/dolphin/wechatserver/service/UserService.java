package com.dolphin.wechatserver.service;

import com.dolphin.wechatserver.bean.User;
import com.dolphin.wechatserver.dao.UserDao;
import com.dolphin.wechatserver.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信用户业务逻辑处理
 *
 * @author Dolphin
 * @date 2025/01/21
 */
@Service
@EnableScheduling
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao;

    // 请求失败码
    private static Integer FAIL_CODE_ACCOUNT_NOT_EXIST = 1; // 账号不存在
    private static Integer FAIL_CODE_PASSWORD_ERROR = 2; // 密码错误
    private static Integer FAIL_CODE_USER_ALREADY_EXIST = 3; // 用户已存在
    private static Integer FAIL_CODE_USER_ID_NOT_EXIST = 4; // 用户 ID 不存在
    private static Integer FAIL_CODE_PHONE_NUMBER_ERROR = 5; // 手机号错误
    private static Integer FAIL_CODE_REQUEST_PARAMS_ERROR = 6; // 请求参数错误
    private static Integer FAIL_CODE_PHONE_NUMBER_NEVER_REGISTER = 7; // 手机号未注册

    public ResponseData registerWeChatUser(User user) {
        User selectUser = getUserByPhoneNumber(user.getPhoneNumber());
        if (selectUser != null) {
            return ResponseData.createFailResponseData(FAIL_CODE_USER_ALREADY_EXIST);
        }
        // 生成随机微信号(这里为了方便直接使用 wxid_手机号)
        user.setAccount("wxid_" + user.getPhoneNumber());
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(user.getCreateTime());
        userDao.insertUser(user);
        user.setPassword("");
        return ResponseData.createSuccessResponseData("user", user);
    }

    public ResponseData getUserInfoByID(Integer userID) {
        User user = userDao.selectOnyByUserID(userID);
        if (user == null) {
            return ResponseData.createFailResponseData(FAIL_CODE_USER_ID_NOT_EXIST);
        }
        user.setPassword("");
        return ResponseData.createSuccessResponseData("user", user);
    }

    public ResponseData loginByAccountAndPassword(Map<String, String> requestParams) {
        if (!requestParams.containsKey("account") || !requestParams.containsKey("password")) {
            return ResponseData.createFailResponseData(FAIL_CODE_REQUEST_PARAMS_ERROR);
        }
        String account = requestParams.get("account");
        String password = requestParams.get("password");
        User user = userDao.selectOneByAccount(account);
        if (user == null) {
            return ResponseData.createFailResponseData(FAIL_CODE_ACCOUNT_NOT_EXIST);
        }
        if (user.getPassword().equals(password)) {
            user.setPassword("");
            return ResponseData.createSuccessResponseData("user", user);
        }
        return ResponseData.createFailResponseData(FAIL_CODE_PASSWORD_ERROR);
    }

    public ResponseData loginByPhoneAndVerifyCode(String phoneNumber) {
        User user = userDao.selectOneByPhoneNumber(phoneNumber);
        if (user == null) {
            return ResponseData.createFailResponseData(FAIL_CODE_ACCOUNT_NOT_EXIST);
        }
        user.setPassword("");
        return ResponseData.createSuccessResponseData("user", user);
    }

    public ResponseData loginByPhoneNumberAndPassword(Map<String, String> requestParams) {
        if (!requestParams.containsKey("phoneNumber") || !requestParams.containsKey("password")) {
            return ResponseData.createFailResponseData(FAIL_CODE_REQUEST_PARAMS_ERROR);
        }
        String phoneNumber = requestParams.get("phoneNumber");
        String password = requestParams.get("password");
        User user = getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            return ResponseData.createFailResponseData(FAIL_CODE_PHONE_NUMBER_NEVER_REGISTER);
        }
        if (password.equals(user.getPassword())) {
            user.setPassword("");
            return ResponseData.createSuccessResponseData("user", user);
        }
        return ResponseData.createFailResponseData(FAIL_CODE_PASSWORD_ERROR);
    }

    // 根据手机号获取用户信息
    private User getUserByPhoneNumber(String phoneNumber) {
        log.info("UserService getUserByPhoneNumber");
        return userDao.selectOneByPhoneNumber(phoneNumber);
    }

}
