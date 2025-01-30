package com.dolphin.wechatserver.exception;

/**
 * @author Dolphin
 * @date 2025/01/21
 */
public class RequestForbiddenException extends RuntimeException {

    public RequestForbiddenException(String message) {
        super(message);
    }

}
