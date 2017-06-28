package com.childcare.app.data.model;

import java.io.Serializable;

/**
 * 服务器返回数据类
 *
 * @param <T> 业务数据类
 * @author john
 * @since 2017-03-23
 */
public class Resp<T> implements Serializable {
    /**
     * 返回码-成功
     */
    private static final int SUCCESS = 0;
    /**
     * 返回码
     */
    private int code;
    /**
     * 消息
     */
    private String message;
    /**
     * 业务数据
     */
    private T data;

    public Resp(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断请求是否成功
     *
     * @return 成功返回true, 否则返回false
     */
    public boolean isSuccessful() {
        return code == SUCCESS;
    }
}
