package com.monit.util;

public class Util {

    public static String getStringFromArray(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for(String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }
}
