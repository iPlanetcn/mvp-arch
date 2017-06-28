package com.childcare.app.ui.splash;

import com.childcare.app.data.DataManager;
import com.childcare.app.ui.base.BasePresenter;
import com.childcare.app.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 启动界面Presenter
 *
 * @author john
 * @since 2017-03-28
 */
public class SplashPresenter extends BasePresenter<SplashContract.View>
        implements SplashContract.Presenter {

    @Inject
    SplashPresenter(DataManager dataManager,
                    SchedulerProvider schedulerProvider,
                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
