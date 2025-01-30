package com.dolphin.wechatserver.exception;

/**
 * @author Dolphin
 * @date 2025/01/21
 */
public class ServerBusyException extends RuntimeException {

    public ServerBusyException(String message) {
        super(message);
    }

}
