package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.HandyBaseUtils;

/**
 * Application基本类
 * Created by LiuJie on 2016/5/7.
 */
public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            HandyBaseUtils.registerContext(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
