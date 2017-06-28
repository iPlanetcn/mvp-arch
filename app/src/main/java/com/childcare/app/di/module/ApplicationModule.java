package com.childcare.app.di.module;

import android.app.Application;
import android.content.Context;

import com.childcare.app.data.remote.GrowthCareService;
import com.childcare.app.di.ApplicationContext;
import com.childcare.app.di.DatabaseInfo;
import com.childcare.app.di.PreferencesInfo;
import com.childcare.app.util.constant.AppConfigs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger2-Application模块,提供Application层级依赖
 *
 * @author john
 * @since 2017-03-20
 */

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    GrowthCareService provideGrowthCareService() {
        return GrowthCareService.Creator.newGrowthCareService();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConfigs.DATA_BASE_NAME;
    }

    @Provides
    @PreferencesInfo
    String providePreferencesFileName() {
        return AppConfigs.PREFERENCES_FILE_NAME;
    }
}
