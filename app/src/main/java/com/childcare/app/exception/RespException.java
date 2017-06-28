package com.childcare.app.exception;

import com.childcare.app.data.model.Resp;

/**
 * 返回数据异常,根据返回错误码,抛出对应的异常
 *
 * @author john
 * @since 2017-03-29
 */
public class RespException extends RuntimeException {
    /**
     * 请求失败
     */
    private static final int FAILURE = -1;
    /**
     * 非法设备
     */
    private static final int ILLEGAL_DEVICE = -2;
    /**
     * 登录失效
     */
    private static final int LOGIN_EXPIRE = -3;

    public RespException(Resp resp) {
        switch (resp.getCode()) {
            case FAILURE:
                throw new FailureException(resp.getMessage());
            case ILLEGAL_DEVICE:
                throw new IllegalDeviceException(resp.getMessage());
            case LOGIN_EXPIRE:
                throw new LoginExpireException(resp.getMessage());
        }
    }

}
