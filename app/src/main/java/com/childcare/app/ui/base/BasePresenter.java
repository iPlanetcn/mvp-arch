package com.childcare.app.ui.base;

import com.childcare.app.data.DataManager;
import com.childcare.app.data.model.Resp;
import com.childcare.app.exception.FailureException;
import com.childcare.app.exception.IllegalDeviceException;
import com.childcare.app.exception.LoginExpireException;
import com.childcare.app.exception.RespException;
import com.childcare.app.util.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * MVP框架-Presenter基类
 *
 * @author john
 * @since 2017-03-21
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    private V mView;

    public BasePresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
        mView = null;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    private boolean isViewAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }

    /**
     * 错误及异常处理
     */
    protected void handleError(Throwable e) {
        Timber.e(e.getMessage(), e.getCause());
        if (!isViewAttached()) {
            return;
        }

        mView.showLoadingIndicator(false);
        if (e instanceof IllegalDeviceException) {
            mView.openSplashActivity();
        } else if (e instanceof LoginExpireException) {
            mView.showMessage(e.getMessage());
            mView.openLoginActivity();
        } else if (e instanceof FailureException) {
            mView.showMessage(e.getMessage());
        }
    }

    /**
     * 处理数据基类(消费者)
     *
     * @param <T> 返回数据泛型
     */
    protected abstract class RespConsumer<T> implements Consumer<Resp<T>> {

        @Override
        public void accept(Resp<T> resp) throws Exception {
            if (!isViewAttached()) {
                return;
            }

            hideLoadingIndicator();
            if (resp.isSuccessful()) {
                success(resp.getData());
            } else {
                throw new RespException(resp);
            }
        }

        /**
         * 隐藏进度条,可覆盖
         */
        protected void hideLoadingIndicator() {
            mView.showLoadingIndicator(false);
        }

        /**
         * 成功处理
         *
         * @param data 返回数据
         */
        protected abstract void success(T data);
    }


    /**
     * 添加观察订阅事件
     */
    protected <T> void addSubscription(Observable<T> observable, Consumer<? super T> onNext) {
        getView().showLoadingIndicator(true);
        addSubscription(observable, onNext, this::handleError);
    }


    /**
     * 添加观察订阅事件(包含错误处理)
     * 注意:调用该方法前,必须先调用或自定义getView().showLoadingIndicator(true);
     */
    protected <T> void addSubscription(Observable<T> observable,
                                       Consumer<? super T> onNext,
                                       Consumer<? super Throwable> onError) {
        getCompositeDisposable().add(observable.subscribeOn(getSchedulerProvider().io())
                                               .observeOn(getSchedulerProvider().ui())
                                               .subscribe(onNext, onError));
    }

}