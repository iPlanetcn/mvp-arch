package com.childcare.app.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * SharedPreferences信息
 *
 * @author john
 * @since 2017-05-20
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferencesInfo {
}
