package com.childcare.app.exception;

/**
 * 登录失效异常
 *
 * @author john
 * @since 2017-03-29
 */
public class LoginExpireException extends RuntimeException {
    LoginExpireException(String message) {
        super(message);
    }
}
