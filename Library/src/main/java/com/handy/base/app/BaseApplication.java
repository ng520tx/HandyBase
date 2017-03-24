package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.CleanUtils;
import com.handy.base.utils.CrashUtils;
import com.handy.base.utils.HandyBaseUtils;
import com.handy.base.utils.LogUtils;

/**
 * Application基本类
 * Created by LiuJie on 2016/5/7.
 */
public abstract class BaseApplication extends Application {
    public boolean isInitHandyBaseUtils = true;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            if (isInitHandyBaseUtils) {
                HandyBaseUtils.getInstance().registerUtils(getApplicationContext());
                CleanUtils.getInstance().cleanInternalCache();
                CleanUtils.getInstance().cleanExternalCache();
                CrashUtils.getInstance().initCrashUtils();
                LogUtils.getInstance().initLogUtils(true, true, 'v', "HandyLog");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getInstance().w(null, "", e);
        }
    }
}
