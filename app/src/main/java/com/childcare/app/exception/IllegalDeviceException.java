package com.childcare.app.exception;

/**
 * 非法设备异常
 *
 * @author john
 * @since 2017-03-29
 */
public class IllegalDeviceException extends RuntimeException {
    IllegalDeviceException(String message) {
        super(message);
    }
}
