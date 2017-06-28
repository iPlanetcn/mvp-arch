package com.childcare.app.ui.base;

/**
 * MVP框架-Presenter接口
 *
 * @author john
 * @since 2017-03-21
 */
public interface MvpPresenter<T extends MvpView> {

    void attachView(T mvpView);

    void detachView();
}
