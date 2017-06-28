package com.childcare.app.di.component;

import android.app.Application;
import android.content.Context;

import com.childcare.app.data.DataManager;
import com.childcare.app.di.ApplicationContext;
import com.childcare.app.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2-Application组件
 *
 * @author john
 * @since 2017-03-20
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();
}
