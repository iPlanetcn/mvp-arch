package com.childcare.app.di.component;


import com.childcare.app.di.ConfigPersistent;
import com.childcare.app.di.module.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}