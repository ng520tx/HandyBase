package com.handy.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;
    private static WeakReference<Activity> sCurrentActivityWeakRef;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
    }

    public static void setActivity(@NonNull Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

    /**
     * 获取Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getAppContext() {
        if (sApplication != null) return sApplication.getApplicationContext();
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取Activity
     *
     * @return Activity
     */
    public static Activity getCurrentActivity() {
        if (sCurrentActivityWeakRef != null) return sCurrentActivityWeakRef.get();
        throw new NullPointerException("u should setActivity first");
    }
}
