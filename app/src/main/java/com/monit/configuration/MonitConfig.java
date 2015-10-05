package com.monit.configuration;

import android.support.design.widget.Snackbar;

public class MonitConfig {

    public static Snackbar snackbarInfo;
    public static boolean isUpdated = true;
    private static boolean autoRefresh = false;

    private static String baseUrl = "http://54.68.38.254";
    private static String previousUrl = baseUrl;
    private static String target = "summarize(stats.teste,'1min','avg')";
    private static String format = "json";
    private static String fromTime = "30";
    private static String fromType = "min";

    public static final String SECONDS = "sec";
    public static final String MINUTES = "min";
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60000;
    private static long timeRefresh = 10_000;

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        MonitConfig.previousUrl = MonitConfig.baseUrl;
        MonitConfig.baseUrl = baseUrl;
    }

    public static String getTarget() {
        return target;
    }

    public static String getFormat() {
        return format;
    }

    public static String getFrom() {
        return "-" + MonitConfig.fromTime + MonitConfig.fromType;
    }

    public static void rollback() {
        MonitConfig.baseUrl = MonitConfig.previousUrl;
    }

    public static void setAutoRefresh(boolean isChecked) {
        MonitConfig.autoRefresh = isChecked;
    }

    public static boolean isAutoRefresh() {
        return autoRefresh;
    }

    public static long getTimeRefresh() {
        return timeRefresh;
    }

    public static void setTimeRefresh(String number, String time) {
        switch (time) {
            case SECONDS:
                MonitConfig.timeRefresh = Long.parseLong(number) * ONE_SECOND;
                break;
            case MINUTES:
                MonitConfig.timeRefresh = Long.parseLong(number) * ONE_MINUTE;
                break;
        }
    }

    public static void setFrom(String fromTime, String fromType) {
        MonitConfig.fromTime = fromTime;
        MonitConfig.fromType = fromType;
    }

    public static void setTarget(String target) {
        MonitConfig.target = target;
    }
}
