package com.dolphin.wechatserver.handler;

import com.dolphin.wechatserver.exception.RequestForbiddenException;
import com.dolphin.wechatserver.exception.ServerBusyException;
import com.dolphin.wechatserver.exception.UploadFileException;
import com.dolphin.wechatserver.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常处理
 *
 * @author Dolphin
 * @date 2025/01/21
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    private static final Integer KNOWN_EXCEPTION = 500; // 服务端异常
    private static final Integer SERVER_BUSY = 503; // 服务端繁忙
    private static final Integer UNKNOWN_EXCEPTION = 999; // 未知异常
    private static final Integer REQUEST_FORBIDDEN = 403; // 请求拒绝处理
    private static final Integer METHOD_NOT_SUPPORTED = 405; // 请求方式不对

    @ExceptionHandler(Exception.class)
    public ResponseData handler(Exception e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        log.info("出现异常, 异常类型: {}", e.toString());
        log.info("异常位置: {} 类的第 {} 行, 出现异常的方法: {}", stackTraceElement.getClassName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName());
        if (e.getClass() == HttpRequestMethodNotSupportedException.class) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            return ResponseData.createFailResponseData(METHOD_NOT_SUPPORTED, "请求方式不被支持");
        }
        if (e.getClass() == RequestForbiddenException.class) {
            RequestForbiddenException exception = (RequestForbiddenException) e;
            return ResponseData.createFailResponseData(REQUEST_FORBIDDEN, exception.getMessage());
        }
        if (e.getClass() == ServerBusyException.class) {
            ServerBusyException exception = (ServerBusyException) e;
            return ResponseData.createFailResponseData(SERVER_BUSY, exception.getMessage());
        }
        if (e.getClass() == UploadFileException.class) {
            UploadFileException exception = (UploadFileException) e;
            return ResponseData.createFailResponseData(KNOWN_EXCEPTION, exception.getMessage());
        }
        return ResponseData.createFailResponseData(UNKNOWN_EXCEPTION, "服务器崩溃");
    }

}
