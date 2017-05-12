package com.handy.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;

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
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Activity getActivity() {
        if (activity != null) return activity;
        throw new NullPointerException("u should init first");
    }
}