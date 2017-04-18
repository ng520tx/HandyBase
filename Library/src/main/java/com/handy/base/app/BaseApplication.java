package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.AppUtils;
import com.handy.base.utils.CleanUtils;
import com.handy.base.utils.CrashUtils;
import com.handy.base.utils.LogUtils;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Application基本类
 * Created by LiuJie on 2016/5/7.
 */
public abstract class BaseApplication extends Application {
    public boolean isCleanCache = true;
    public boolean isInitHandyBaseUtils = true;
    public boolean isInitPgyCrashManager = false;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            if (isInitHandyBaseUtils) {
                if (isCleanCache) { //清空手机内部和外部缓存数据
                    CleanUtils.getInstance().cleanInternalCache(getApplicationContext());
                    CleanUtils.getInstance().cleanExternalCache(getApplicationContext());
                }

                CrashUtils.getInstance().init(getApplicationContext()); //初始化崩溃捕获工具

                LogUtils.getInstance().initBuilder(getApplicationContext())
                        .setLogSwitch(AppUtils.getInstance().isAppDebug(getApplicationContext()))// 设置log总开关，默认开
                        .setGlobalTag("HandyBase")// 设置log全局标签，默认为空
                        // 当全局标签不为空时，我们输出的log全部为该tag，
                        // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                        .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                        .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                        .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose

                LogUtils.getInstance().d("工具类功能已注册");
            }

            if (isInitPgyCrashManager) {
                PgyCrashManager.register(getApplicationContext());
                LogUtils.getInstance().d("蒲公英内测功能已注册");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.getInstance().w(null, "", e);
        }
    }
}
