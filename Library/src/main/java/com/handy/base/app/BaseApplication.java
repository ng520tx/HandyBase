package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.AppUtils;
import com.handy.base.utils.CrashUtils;
import com.handy.base.utils.LogUtils;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Application基本类
 * </pre>
 */
public abstract class BaseApplication extends Application {
    public boolean isInitUtils = true;
    public boolean isInitPgyCrashManager = false;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            /* 初始化工具类功能 */
            if (isInitUtils) {
                CrashUtils.getInstance().init(getApplicationContext()); //初始化崩溃捕获工具

                if (AppUtils.getInstance().isAppDebug(getApplicationContext())) {
                    LogUtils.getInstance().initBuilder(getApplicationContext())
                            .setLogSwitch(AppUtils.getInstance().isAppDebug(getApplicationContext())) //设置log总开关，默认开
                            .setGlobalTag("HDB") //设置log全局标签，默认为空，当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
                            .setLog2FileSwitch(true) //打印log时是否存到文件的开关，默认关
                            .setEncryptSwitch(false) //存到文件的log内容加密，默认关
                            .setBorderSwitch(true) //输出日志是否带边框开关，默认开
                            .setLogFilter(LogUtils.V); //log过滤器，和logcat过滤器同理，默认Verbose
                } else {
                    LogUtils.getInstance().initBuilder(getApplicationContext())
                            .setLogSwitch(AppUtils.getInstance().isAppDebug(getApplicationContext())) //设置log总开关，默认开
                            .setGlobalTag("") //设置log全局标签，默认为空，当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
                            .setLog2FileSwitch(true) //打印log时是否存到文件的开关，默认关
                            .setEncryptSwitch(true) //存到文件的log内容加密，默认关
                            .setBorderSwitch(false) //输出日志是否带边框开关，默认开
                            .setLogFilter(LogUtils.V); //log过滤器，和logcat过滤器同理，默认Verbose
                }
            }
            /* 初始化蒲公英内测功能 */
            if (isInitPgyCrashManager) {
                PgyCrashManager.register(getApplicationContext());
                LogUtils.getInstance().d("蒲公英内测功能已注册");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
