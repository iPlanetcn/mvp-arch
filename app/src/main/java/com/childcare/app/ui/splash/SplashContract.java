package com.childcare.app.ui.splash;

import com.childcare.app.ui.base.MvpPresenter;
import com.childcare.app.ui.base.MvpView;

/**
 * 启动界面MVP-VP契约
 *
 * @author john
 * @since 2017-03-28
 */
interface SplashContract {
    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
