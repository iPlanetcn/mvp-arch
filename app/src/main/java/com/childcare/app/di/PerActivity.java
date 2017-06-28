package com.childcare.app.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * PerActivity Annotation
 *
 * @author john
 * @since 2017-03-20
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
