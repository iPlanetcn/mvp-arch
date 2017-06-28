package com.childcare.app.ui.base;

import android.support.annotation.StringRes;

/**
 * MVP框架-View接口
 *
 * @author john
 * @since 2017-03-21
 */
public interface MvpView {

    /**
     * 显示加载进度条
     *
     * @param show true:显示,false:隐藏
     */
    void showLoadingIndicator(boolean show);

    /**
     * 显示加载进度条
     *
     * @param show    true:显示,false:隐藏
     * @param massage 消息内容
     */
    void showLoadingIndicator(boolean show, String massage);

    /**
     * 显示加载进度条
     *
     * @param show  true:显示,false:隐藏
     * @param resId 消息内容资源ID
     */
    void showLoadingIndicator(boolean show, @StringRes int resId);

    /**
     * 显示消息
     *
     * @param message 消息内容
     */
    void showMessage(String message);

    /**
     * 显示消息
     *
     * @param resId 消息内容资源ID
     */
    void showMessage(@StringRes int resId);

    /**
     * 打开登录界面
     */
    void openLoginActivity();

    /**
     * 打开启动页
     */
    void openSplashActivity();

    /**
     * 隐藏键盘
     */
    void hideKeyboard();

    /**
     * 网络是否连接
     */
    boolean isNetworkConnected();
}
