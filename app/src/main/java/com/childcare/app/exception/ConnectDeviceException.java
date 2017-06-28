package com.childcare.app.exception;

/**
 * 连接蓝牙设备异常
 *
 * @author john
 * @since 2017-04-27
 */
public class ConnectDeviceException extends RuntimeException {
    public ConnectDeviceException(String message) {
        super(message);
    }
}
