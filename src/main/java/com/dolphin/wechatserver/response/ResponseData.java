package com.dolphin.wechatserver.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应数据
 * @author Dolphin
 * @date 2025/01/21
 */
public class ResponseData {

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        System.out.println(date);
    }

    public Map<Object, Object> responseData = new HashMap<>(4);

    /**
     * 设置请求成功返回信息
     * @param infoKey 请求数据 key
     * @param infoData 请求数据
     */
    private void setSuccessData(String infoKey, Object infoData) {
        responseData.put("requestSuccess", true);
        responseData.put(infoKey, infoData);
    }

    /**
     * 创建请求成功返回数据对象
     * @param infoKey 请求数据 key
     * @param infoData 请求数据
     * @return 返回 ResponseData 对象
     */
    public static ResponseData createSuccessResponseData(String infoKey, Object infoData) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccessData(infoKey, infoData);
        return responseData;
    }

    /**
     * 创建请求失败返回数据对象
     * @return 返回 ResponseData 对象
     */
    public static ResponseData createFailResponseData(Integer failCode) {
        ResponseData responseDataObj = new ResponseData();
        responseDataObj.responseData.put("requestSuccess", false);
        responseDataObj.responseData.put("failCode", failCode);
        return responseDataObj;
    }

    public static ResponseData createFailResponseData(Integer failCode, String failMessage) {
        ResponseData responseDataObj = createFailResponseData(failCode);
        responseDataObj.responseData.put("failMessage", failMessage);
        return responseDataObj;
    }

}
