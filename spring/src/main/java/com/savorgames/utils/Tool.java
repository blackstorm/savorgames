package com.savorgames.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
    private static final String emailAddressPattern =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 判断是否Email
     * @BUG修复 不要将值写反
     * @param str
     * @return
     */
    public static boolean isEmail(String str){
        Pattern regex = Pattern.compile(emailAddressPattern);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }
}
