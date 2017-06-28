package com.childcare.app.data;

import com.childcare.app.data.local.PreferencesHelper;
import com.childcare.app.data.model.Resp;
import com.childcare.app.data.remote.GrowthCareService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * 数据管理器
 *
 * @author john
 * @since 2017-03-20
 */
@Singleton
public class DataManager {
    private final PreferencesHelper mPreferencesHelper;
    private final GrowthCareService mGrowthCareService;


    @Inject
    public DataManager(PreferencesHelper preferencesHelper,
                       GrowthCareService growthCareService) {
        mPreferencesHelper = preferencesHelper;
        mGrowthCareService = growthCareService;
    }

    /**
     * 测试
     */
    public Observable<Resp> test() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("deviceId", "123");
        paramMap.put("doctorId", "0010001001");
        paramMap.put("secretKey", "aa1");
        paramMap.put("name", "test");
        return mGrowthCareService.test(paramMap);
    }
}
