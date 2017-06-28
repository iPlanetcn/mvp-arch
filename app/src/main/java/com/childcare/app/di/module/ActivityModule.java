package com.childcare.app.di.module;

import android.app.Activity;
import android.content.Context;

import com.childcare.app.di.ActivityContext;
import com.childcare.app.util.rx.AppScheduleProvider;
import com.childcare.app.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Dagger2-Activity模块,使用它来传递Context和Activity依赖
 *
 * @author john
 * @since 2017-03-20
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }


    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppScheduleProvider();
    }
}
