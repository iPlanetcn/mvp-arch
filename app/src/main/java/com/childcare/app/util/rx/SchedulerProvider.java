package com.childcare.app.util.rx;

import io.reactivex.Scheduler;

/**
 * 线程调度接口
 *
 * @author john
 * @since 2017-05-09
 */
public interface SchedulerProvider {
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
