package com.childcare.app.ui.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.childcare.app.GrowthCareApp;
import com.childcare.app.R;
import com.childcare.app.di.component.ActivityComponent;
import com.childcare.app.di.component.ConfigPersistentComponent;
import com.childcare.app.di.component.DaggerConfigPersistentComponent;
import com.childcare.app.di.module.ActivityModule;
import com.childcare.app.ui.splash.SplashActivity;
import com.childcare.app.util.DialogUtils;
import com.childcare.app.util.KeyboardUtils;
import com.childcare.app.util.NetworkUtils;
import com.childcare.app.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Activity基类
 *
 * @author john
 * @since 2017-03-21
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements MvpView {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    @SuppressLint("UseSparseArrays")
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();


    private long mActivityId;
    private ProgressDialog mProgressDialog;
    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent
                    .builder()
                    .applicationComponent(GrowthCareApp.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * SnackBar消息
     *
     * @param message 消息内容
     */
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    /**
     * 显示进度条
     *
     * @param show true显示,false隐藏,默认显示message"加载中..."
     */
    public void showLoadingIndicator(boolean show) {
        showLoadingIndicator(show, getString(R.string.loading));
    }

    /**
     * 显示进度条
     *
     * @param show    true显示,false隐藏
     * @param message 自定义显示message"加载中..."
     */
    public void showLoadingIndicator(boolean show, String message) {
        if (show) {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
            mProgressDialog = DialogUtils.getProgressDialog(this, message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void showLoadingIndicator(boolean show, @StringRes int resId) {
        showLoadingIndicator(show, getString(resId));
    }


    public void showMessage(String message) {
        ToastUtils.show(getApplicationContext(), message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        ToastUtils.show(getApplicationContext(), resId);
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击返回按钮,事件处理
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            showCloseActivityTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置标题栏
     */
    public void setupToolbar() {
        if (getSupportActionBar() == null && findViewById(R.id.toolbar) != null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            setTitle(getTitle());
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
        if (getSupportActionBar() != null && findViewById(R.id.toolbar_title) != null) {
            ((TextView) findViewById(R.id.toolbar_title)).setText(title);
        }
    }

    @Override
    public void openLoginActivity() {
    }

    @Override
    public void openSplashActivity() {
        Intent intent = SplashActivity.getStartIntent(this, true);
        startActivity(intent);
    }

    /**
     * 打开Activity的切换动画(当全局切换动画无效时调用)
     */
    protected void showOpenActivityTransition() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 关闭Activity的切换动画(当全局切换动画无效时调用)
     */
    protected void showCloseActivityTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 检查指定服务是否在运行
     *
     * @param serviceClass 指定服务类
     * @return 正在运行:true,否则false
     */
    protected boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
