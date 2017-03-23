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

    private volatile static HandyBaseUtils instance;
    private Context context;

    /**
     * 获取单例
     */
    public static HandyBaseUtils getInstance() {
        if (instance == null) {
            synchronized (HandyBaseUtils.class) {
                if (instance == null) {
                    instance = new HandyBaseUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 注册工具类的Context
     *
     * @param context 上下文
     */
    public void registerContext(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public Context getContext() {
        if (this.context != null) return this.context;
        throw new NullPointerException("u should registerContext first");
    }
}