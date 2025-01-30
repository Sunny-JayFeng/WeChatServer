package com.dolphin.wechatserver.constant;

/**
 * @author Dolphin
 * @date 2025/01/21
 */
public enum FilePathEnum {

//    ADMIN_BASIC_FILE_PATH("/opt/local/static/admin/file/"),

//    SELLER_BASIC_FILE_PATH("/opt/local/static/seller/file/"),

//    USER_BASIC_FILE_PATH("/opt/local/static/user/file/"),

    USER_AVATAR_FILE_PATH("E:/MyProject/WeChat/resources/avatar/"), // 头像文件存放路径

    USER_MOMENTS_FILE_PATH("E:/MyProject/WeChat/resources/moments/"), // 朋友圈文件存放路径

    USER_MOMENTS_BKG_FILE_PATH("E:/MyProject/WeChat/resources/momentBkg/"), // 朋友圈背景图

    CHAT_BKG_FILE_PATH("E:/MyProject/WeChat/resources/chatBkg/"); // 聊天背景图


    private String basicFilePath;

    FilePathEnum(String basicFilePath) {
        this.basicFilePath = basicFilePath;
    }

    public String basicFilePath() {
        return this.basicFilePath;
    }

}
