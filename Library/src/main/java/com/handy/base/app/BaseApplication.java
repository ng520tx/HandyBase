package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.CleanUtils;
import com.handy.base.utils.CrashUtils;
import com.handy.base.utils.HandyBaseUtils;
import com.handy.base.utils.LogUtils;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Application基本类
 * Created by LiuJie on 2016/5/7.
 */
public abstract class BaseApplication extends Application {
    public boolean isInitHandyBaseUtils = true;
    public boolean isInitPgyCrashManager = false;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            if (isInitHandyBaseUtils) {
                HandyBaseUtils.getInstance().registerUtils(getApplicationContext());

                /* 清空手机内部和外部缓存数据 */
                CleanUtils.getInstance().cleanInternalCache();
                CleanUtils.getInstance().cleanExternalCache();

                CrashUtils.getInstance().initCrashUtils(); //初始化崩溃捕获工具
                LogUtils.getInstance().initLogUtils(true, true, 'v', "HandyLog"); //初始化日志输出工具
            }

            if (isInitPgyCrashManager) {
                PgyCrashManager.register(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getInstance().w(null, "", e);
        }
    }
}
