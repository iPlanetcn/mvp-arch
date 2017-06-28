package com.childcare.app.di.component;

import com.childcare.app.di.PerActivity;
import com.childcare.app.di.module.ActivityModule;
import com.childcare.app.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Dagger2-Activity组件
 *
 * @author john
 * @since 2017-03-20
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);
}