package com.childcare.app.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.childcare.app.R;
import com.childcare.app.ui.base.BaseActivity;
import com.childcare.app.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 启动界面
 *
 * @author john
 * @since 2017-03-21
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject
    SplashPresenter mPresenter;

    @BindView(R.id.tv_info)
    TextView mTvInfo;

    /**
     * 获取启动SplashActivity的Intent
     *
     * @param context                 上下文
     * @param clearPreviousActivities 是否清除任务栈
     * @return Intent
     */
    public static Intent getStartIntent(@NonNull Context context,
                                        boolean clearPreviousActivities) {
        Intent intent = new Intent(context, SplashActivity.class);
        if (clearPreviousActivities) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.attachView(this);

//        if (BuildConfig.DEBUG) {
//            String buildConfigInfo = new GsonBuilder().excludeFieldsWithModifiers(TRANSIENT)
//                    .create()
//                    .toJson(new BuildConfig())
//                    .replace(",", "\n");
//            mTvInfo.setText(buildConfigInfo);
//        }

        mTvInfo.setText(R.string.info);

        findViewById(R.id.btn_open_main).setOnClickListener(v -> {
            startActivity(MainActivity.getStartIntent(SplashActivity.this, true));
           showOpenActivityTransition();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
