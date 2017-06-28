package com.childcare.app.data.remote;

import com.childcare.app.BuildConfig;
import com.childcare.app.data.model.Resp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * GrowthCare服务端服务接口
 *
 * @author john
 * @since 2017-03-21
 */
public interface GrowthCareService {
    /**
     * 测试
     */
    @FormUrlEncoded
    @POST("test/myPost.go")
    Observable<Resp> test(@FieldMap Map<String, Object> params);

    /**
     * 检查设备合法性
     */
    @GET("checkDevice/{id}.go")
    Observable<Resp<Boolean>> checkDevice(@Path("id") String deviceId);


    /******** Helper class that sets up a new services *******/
    class Creator {
        public static GrowthCareService newGrowthCareService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                logging.setLevel(Level.BODY);
            } else {
                logging.setLevel(Level.BASIC);
            }

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            return retrofit.create(GrowthCareService.class);
        }
    }
}
