package com.childcare.app.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.childcare.app.di.component.ActivityComponent;

import butterknife.Unbinder;

/**
 * Fragment基类
 *
 * @author john
 * @since 2017-03-27
 */

@SuppressWarnings("unused")
public class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        if (mActivity != null) {
            mActivity.showLoadingIndicator(show);
        }
    }

    @Override
    public void showLoadingIndicator(boolean show, String massage) {
        if (mActivity != null) {
            mActivity.showLoadingIndicator(show, massage);
        }
    }

    @Override
    public void showLoadingIndicator(boolean show, @StringRes int resId) {
        if (mActivity != null) {
            mActivity.showLoadingIndicator(show, resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public void openLoginActivity() {
        if (mActivity != null) {
            mActivity.openLoginActivity();
        }
    }

    @Override
    public void openSplashActivity() {
        if (mActivity != null) {
            mActivity.openSplashActivity();
        }
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }


    public ActivityComponent getActivityComponent() {
        return mActivity.getActivityComponent();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
}
