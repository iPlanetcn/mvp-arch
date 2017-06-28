package com.childcare.app.data.model;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.childcare.app.data.model.Params.Builder.INVALID_STRING;


/**
 * 服务器请求类(通过构建模式,自行配置请求参数)
 *
 * @author john
 * @since 2017-03-27
 */
@SuppressWarnings("ALL")
final class Params {
    /**
     * 参数字典
     */
    private final Map<String, Object> mParamMap;

    /**
     * 转换成Json
     *
     * @return json字符串
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(mParamMap);
    }

    /**
     * 获取参数字典
     */
    public Map<String, Object> getParamMap() {
        return mParamMap;
    }

    /**
     * 定义键
     */
    private final class KEY {
        static final String DEVICE_ID = "deviceId";

    }

    /**
     * 参数构造器
     */
    private Params(Builder builder) {
        mParamMap = new HashMap<>();
        if (!INVALID_STRING.equals(builder.deviceId)) {
            mParamMap.put(KEY.DEVICE_ID, builder.deviceId);
        }

    }

    /**
     * 参数构建类
     */
    public static class Builder {
        /* 定义非法的值,初始化的值均为非法值 */
        static final Integer INVALID_INTEGER = -1;
        static final Float INVALID_FLOAT = -1f;
        static final Double INVALID_DOUBLE = -1d;
        static final String INVALID_STRING = "-1";

        /* 变量 */
        private String deviceId = INVALID_STRING;

        /* no-op */
        private Builder() {
        }

        public Params build() {
            return new Params(this);
        }

        public Builder deviceId(String val) {
            deviceId = val;
            return this;
        }

    }
}
