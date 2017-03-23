package com.handy.base.utils;

import android.content.Context;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class HandyBaseUtils {

    private static Context context;

    private HandyBaseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 注册工具类的Context
     *
     * @param context 上下文
     */
    public static void registerContext(Context context) {
        HandyBaseUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should registerContext first");
    }
}