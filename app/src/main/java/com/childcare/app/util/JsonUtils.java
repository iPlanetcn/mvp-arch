package com.childcare.app.util;

import android.content.res.AssetManager;

import com.childcare.app.GrowthCareApp;
import com.childcare.app.data.model.Resp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

/**
 * Json测试数据工具类
 *
 * @author john
 * @since 2017-03-27
 */
@SuppressWarnings("ALL")
public final class JsonUtils {
    /**
     * 获取检查设备数据
     */
    public static Resp<Boolean> getCheckDeviceData() {
        return new Gson().fromJson(loadJSONFromAsset("json/check_device_data.json"),
                new TypeToken<Resp<Boolean>>() {}.getType());
    }

    /**
     * 从assets目录加载xx.json数据
     */
    private static String loadJSONFromAsset(String fileName) {
        try {
            AssetManager manager = GrowthCareApp.getInstance().getComponent().context().getAssets();
            InputStream is = manager.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Timber.e(ex.getMessage());
            return null;
        }
    }

}
