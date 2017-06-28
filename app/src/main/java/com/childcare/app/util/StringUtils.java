package com.childcare.app.util;

import android.support.annotation.Nullable;

/**
 * 字符串工具类(便于Java单元测试)
 *
 * @author john
 * @since 2017-04-19
 */
public final class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 空:true,非空:false
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
