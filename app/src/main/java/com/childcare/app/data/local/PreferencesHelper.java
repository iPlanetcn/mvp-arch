package com.childcare.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.childcare.app.di.ApplicationContext;
import com.childcare.app.di.PreferencesInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * SharePreference辅助类
 *
 * @author john
 * @since 2017-03-27
 */
@SuppressWarnings("ALL")
@Singleton
public class PreferencesHelper {
    private static final String PREF_KEY_DEVICE_ID = "PREF_KEY_DEVICE_ID";

    private final SharedPreferences mPref;
    private final Gson mGson;


    @Inject
    public PreferencesHelper(@ApplicationContext Context context,
                             @PreferencesInfo String name) {
        mPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create();
    }


    ///////////////////////////////////////////////////////////////////////////
    // 设备编号
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 保存设备编号
     */
    public void saveDeviceId(String deviceId) {
        mPref.edit()
             .putString(PREF_KEY_DEVICE_ID, deviceId)
             .apply();
    }

    /**
     * 读取设备编号
     *
     * @return 医生信息DoctorInfo
     */
    public String readDeviceId() {
        return mPref.getString(PREF_KEY_DEVICE_ID, "");
    }

    /**
     * 清除所有SharePreference数据
     */
    private void clear() {
        mPref.edit()
             .clear()
             .apply();
    }

}
