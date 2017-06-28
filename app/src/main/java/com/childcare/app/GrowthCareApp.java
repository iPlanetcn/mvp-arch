package com.childcare.app;

import android.app.Application;
import android.content.Context;

import com.childcare.app.data.DataManager;
import com.childcare.app.di.component.ApplicationComponent;
import com.childcare.app.di.component.DaggerApplicationComponent;
import com.childcare.app.di.module.ApplicationModule;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


/**
 * 自定义Application
 *
 * @author john
 * @since 2017-03-20
 */
@Singleton
public class GrowthCareApp extends Application {
    @Inject
    DataManager mDataManager;
    private ApplicationComponent mApplicationComponent;
    private static GrowthCareApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static GrowthCareApp getInstance() {
        return mInstance;
    }

    public static GrowthCareApp get(Context context) {
        return (GrowthCareApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent =
                    DaggerApplicationComponent.builder()
                                              .applicationModule(new ApplicationModule(this))
                                              .build();
        }
        return mApplicationComponent;
    }

    /**
     * 用于测试时替换该组件
     *
     * @param applicationComponent 测试组件
     */
    @SuppressWarnings("unused")
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
