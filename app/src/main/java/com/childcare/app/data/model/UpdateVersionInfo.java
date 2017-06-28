package com.childcare.app.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 更新版本信息
 *
 * @author john
 * @since 2017-05-16
 */
public class UpdateVersionInfo {

    /**
     * /**
     * 下载地址
     */
    @SerializedName("downLoadURL")
    private String url;
    /**
     * 版本
     */
    private String version;


    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }
}
