package com.childcare.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;

import timber.log.Timber;

/**
 * 设备工具
 *
 * @author john
 * @since 2017-03-27
 */
@SuppressWarnings("ALL")
public final class DeviceUtils {
    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context 上下文
     * @return true:平板,false:手机
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取运营商名称
     *
     * @param context 上下文
     * @return 运营商名称:1、中国移动，2、中国联通，3、中国电信
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        String providersName = "N/A";
        try {
            @SuppressLint("HardwareIds")
            String imsi = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                providersName = "中国移动";
            } else if (imsi.startsWith("46001")) {
                providersName = "中国联通";
            } else if (imsi.startsWith("46003")) {
                providersName = "中国电信";
            }
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
        return providersName;
    }

    /**
     * 判断sim是否就绪
     *
     * @param context 上下文
     * @return true:sim card ready; false: error
     */
    public static boolean isSimCardReady(Context context) {
        boolean isReady = false;
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int stateCode = telephonyManager.getSimState();
        if (TelephonyManager.SIM_STATE_READY == stateCode) {
            isReady = true;
        }

        return isReady;
    }

    /**
     * 判断是否有sim
     *
     * @param context 上下文
     * @return true:no sim card; false:card ok
     */
    public static boolean isSimAbsent(Context context) {
        boolean isAbsent = false;
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int stateCode = telephonyManager.getSimState();
        if (TelephonyManager.SIM_STATE_ABSENT == stateCode) {
            isAbsent = true;
        }

        if (TelephonyManager.SIM_STATE_UNKNOWN == stateCode) {
            isAbsent = true;
        }
        return isAbsent;
    }

    /**
     * 获取电话号码
     *
     * @param context 上下文
     * @return 11位手机号码
     */
    @SuppressLint("HardwareIds")
    public static String getNativePhoneNumber(Context context) {
        String nativePhoneNumber = "";
        if (isSimAbsent(context)) {
            return nativePhoneNumber;
        }

        //如果有则获取并显示手机号码
        if (isSimCardReady(context)) {
            TelephonyManager tm = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);

            nativePhoneNumber = tm.getLine1Number();
            if (nativePhoneNumber != null && nativePhoneNumber.length() == 14) {
                //去掉国际码
                nativePhoneNumber = nativePhoneNumber.substring(3);
            }
        }
        return nativePhoneNumber;
    }

    /**
     * 获取设备ID
     *
     * @param context mContext
     * @return 设备ID
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 手机型号
     *
     * @return 型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 手机品牌
     *
     * @return 品牌
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取系统SDK版本
     *
     * @return SDK版本
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     *
     * @return 系统版本
     */
    public static String getOsVersion() {
        return "android " + Build.VERSION.RELEASE;
    }


}
