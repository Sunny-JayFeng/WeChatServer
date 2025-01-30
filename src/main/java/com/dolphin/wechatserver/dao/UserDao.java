package com.dolphin.wechatserver.dao;

import com.dolphin.wechatserver.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 微信用户持久层
 *
 * @author dolphin
 * @date 2025-01-21
 */
@Repository
public interface UserDao {

    /**
     * 注册微信
     */
    @Insert("INSERT INTO `user`(`account`, `password`, `phone_number`, `user_name`, `gender`, `area`, `signature`, " +
            "`state`, `avatar`, `create_time`, `update_time`)" +
            "VALUES(#{user.account}, #{user.password}, #{user.phoneNumber}, #{user.userName}, #{user.gender}, " +
            "#{user.area}, #{user.signature}, #{user.state}, #{user.avatar}, #{user.createTime}, #{user.updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(@Param("user") User user);

    /**
     * 根据手机号查询
     */
    @Select("SELECT * FROM `user` WHERE `phone_number` = #{phoneNumber}")
    User selectOneByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 根据 ID 查询
     *
     * @param userID
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `id` = #{userID}")
    User selectOnyByUserID(@Param("userID") Integer userID);

    /**
     * 根据账号查询
     *
     * @param account
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `account` = #{account}")
    User selectOneByAccount(@Param("account") String account);
}
