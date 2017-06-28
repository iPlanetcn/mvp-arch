package com.childcare.app.exception;

/**
 * 读卡异常
 *
 * @author john
 * @since 2017-04-27
 */
public class ReadCardException extends RuntimeException {
    public ReadCardException(String message) {
        super(message);
    }
}
