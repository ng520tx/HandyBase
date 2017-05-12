package com.handy.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param activity 活动界面
     */
    public static void init(Activity activity) {
        Utils.activity = activity;
        Utils.context = activity.getApplicationContext();
    }

    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取Activity
     *
     * @return Activity
     */
    public static Activity getActivity() {
        if (activity != null) return activity;
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getApplicationContext() {
        return context;
    }
}