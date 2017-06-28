package com.childcare.app.util;


import android.content.Context;
import android.widget.Toast;


/**
 * Toast消息工具类
 *
 * @author john
 * @since 2016-07-22
 */
public final class ToastUtils {
    private static Toast mToast;

    public static void show(Context context, int resId) {
        show(context, context.getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (text == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, context.getString(resId, args),
                Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, context.getString(resId, args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
