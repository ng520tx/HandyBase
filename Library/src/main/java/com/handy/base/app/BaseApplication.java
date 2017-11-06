package com.handy.base.app;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.handy.base.utils.androidutilcode.Utils;
import com.pgyersdk.crash.PgyCrashManager;

import org.litepal.LitePal;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Application基本类
 * </pre>
 */
public abstract class BaseApplication extends Application {

    public boolean isInitUtils = true;
    public boolean isUseCustomCrashUtil = true;
    public boolean isInitPgyCrashManager = false;

    /**
     * 日志工具配置类
     */
    public LogUtils.Config config;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Utils.init(getApplicationContext());
            com.blankj.utilcode.util.Utils.init(this);
            LitePal.initialize(getApplicationContext());

             /* 初始化崩溃捕获工具 */
            if (isUseCustomCrashUtil) {
                com.handy.base.utils.CrashUtils.init();
            } else {
                com.blankj.utilcode.util.CrashUtils.init();
            }

            /* 初始化工具类功能 */
            if (isInitUtils) {
                //初始化日志工具
                config = LogUtils.getConfig()
                        // 设置log总开关，包括输出到控制台和文件，默认开
                        .setLogSwitch(AppUtils.isAppDebug())
                        // 设置是否输出到控制台开关，默认开
                        .setConsoleSwitch(AppUtils.isAppDebug())
                        // 设置log全局标签，默认为空。当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
                        .setGlobalTag("HandyBase")
                        // 设置log头信息开关，默认为开
                        .setLogHeadSwitch(true)
                        // 打印log时是否存到文件的开关，默认关
                        .setLog2FileSwitch(AppUtils.isAppDebug())
                        // 当自定义路径为空时，写入应用的/cache/log/目录中
                        .setDir("")
                        // 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                        .setFilePrefix("")
                        // 输出日志是否带边框开关，默认开
                        .setBorderSwitch(false)
                        // log的控制台过滤器，和logcat过滤器同理，默认Verbose
                        .setConsoleFilter(LogUtils.V)
                        // log文件过滤器，和logcat过滤器同理，默认Verbose
                        .setFileFilter(LogUtils.V)
                        // log栈深度，默认为1
                        .setStackDeep(1);
                LogUtils.d(config.toString());
            }

            /* 初始化蒲公英内测功能 */
            if (isInitPgyCrashManager) {
                PgyCrashManager.register(getApplicationContext());
                LogUtils.d("蒲公英内测功能已注册");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
