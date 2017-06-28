package com.childcare.app.exception;

/**
 * 请求失败异常
 *
 * @author john
 * @since 2017-03-29
 */
public class FailureException extends RuntimeException {
    FailureException(String message) {
        super(message);
    }
}
