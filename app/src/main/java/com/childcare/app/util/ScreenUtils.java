package com.childcare.app.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕工具类
 *
 * <P>获取屏幕宽度 {@link #getScreenWidth(Context)}</P>
 * <P>获取屏幕高度 {@link #getScreenHeight(Context)}</P>
 * <P>获取屏幕状态栏高度 {@link #getStatusBarHeight(Context)}</P>
 *
 * @author john
 * @since 2017-03-24
 */
@SuppressWarnings( {"WeakerAccess", "unused"})
public final class ScreenUtils {
    private ScreenUtils() {
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
