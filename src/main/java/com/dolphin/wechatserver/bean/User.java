package com.dolphin.wechatserver.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 微信用户实体类
 *
 * @author dolphin
 * @date 2025-01-20
 */
@Data
public class User {
    // 性别
    public enum Gender {
        Female,
        Male
    }

    // 状态
    public enum State {
        None,
    }

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account; // 账号

    private String password; // 密码

    private String phoneNumber; // 手机号

    private String userName; // 微信名称

    private Integer gender; // 性别

    private String area; // 所在地区

    private String signature; // 个性签名

    private Integer state; // 状态

    private String avatar; // 头像

    private Long createTime;

    private Long updateTime;
}
