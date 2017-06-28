package com.childcare.app.util;

import java.util.regex.Pattern;

/**
 * 正则表达式判断工具类
 *
 * @author john
 * @since 2017-03-29
 */
public final class RegexUtils {
    private static final String REG_ACCOUNT;
    private static final String REG_PASSWORD;

    static {
        REG_PASSWORD = "[A-Z0-9a-z!@#$%^&*.~/\\\\{\\\\}|()'\"?><,.`\\\\+-=_\\\\[\\\\]:;]+";
        REG_ACCOUNT = "[A-Z0-9a-z!@#$%^&*.~/\\\\{\\\\}|()'\\\"?><,.`\\\\+-=_\\\\[\\\\]:;]+";
    }

    /**
     * 匹配正则表达式
     *
     * @param regex 正则表达式
     * @param text  待匹配的文字
     * @return 匹配:true,否则false
     */
    private static boolean checkMatches(String regex, String text) {
        return !StringUtils.isEmpty(text) && Pattern.compile(regex).matcher(text).matches();
    }

    /**
     * 判断账号是否合法
     *
     * @param text 文本
     * @return 合法:true,否则false
     */
    public static boolean checkAccountIsValid(String text) {
        return checkMatches(REG_ACCOUNT, text);
    }

    /**
     * 判断密码是否合法
     *
     * @param text 文本
     * @return 合法:true,否则false
     */
    public static boolean checkPasswordIsValid(String text) {
        return checkMatches(REG_PASSWORD, text);
    }


}
