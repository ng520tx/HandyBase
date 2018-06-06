package com.handy.base.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.base.config.BuglyConfig;
import com.handy.base.utils.androidutilcode.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * 类名
 *
 * @author LiuJie https://www.Handy045.com
 * @description 类功能内容
 * @date Created in 2018/6/6 下午5:09
 * @modified By LiuJie
 */
public class HandyBaseBuild {

    public static String buglyID = "";
    public static BuglyConfig buglyConfig = null;

    public static boolean isUseBugly = true;
    public static boolean isInitLogUtils = true;
    public static boolean isUseCrashUtil = true;

    /**
     * 在第一个启动的Activity或者自定义Application中的onCreate()方法里调用
     */
    @SuppressLint("MissingPermission")
    public static void init(Application application) {
        try {
            /*初始化工具类*/
            Utils.init(application);
            com.blankj.utilcode.util.Utils.init(application);

            /*初始化侧滑返回功能*/
            BGASwipeBackHelper.init(application, null);

            /*初始化数据库*/
            FlowManager.init(application.getApplicationContext());

            /*初始化崩溃捕获工具*/
            if (isUseCrashUtil) {
                CrashUtils.init(new CrashUtils.OnCrashListener() {
                    @Override
                    public void onCrash(String crashInfo, Throwable e) {
                        LogUtils.e(crashInfo);
                        AppUtils.relaunchApp();
                    }
                });
            }

            /*初始化日志工具*/
            if (isInitLogUtils) {
                final LogUtils.Config config = LogUtils.getConfig()
                        // 设置 log 总开关，包括输出到控制台和文件，默认开
                        .setLogSwitch(AppUtils.isAppDebug())
                        // 设置是否输出到控制台开关，默认开
                        .setConsoleSwitch(AppUtils.isAppDebug())
                        // 设置 log 全局标签，默认为空
                        // 当全局标签不为空时，我们输出的 log 全部为该 tag，为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                        .setGlobalTag("HandyBase")
                        // 设置 log 头信息开关，默认为开
                        .setLogHeadSwitch(true)
                        // 打印 log 时是否存到文件的开关，默认关
                        .setLog2FileSwitch(false)
                        // 当自定义路径为空时，写入应用的/cache/log/目录中
                        .setDir("")
                        // 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                        .setFilePrefix("")
                        // 输出日志是否带边框开关，默认开
                        .setBorderSwitch(true)
                        // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                        .setSingleTagSwitch(true)
                        // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                        .setConsoleFilter(LogUtils.V)
                        // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                        .setFileFilter(LogUtils.V)
                        // log 栈深度，默认为 1
                        .setStackDeep(1)
                        // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                        .setStackOffset(0);
                LogUtils.d(config.toString());
            }

            /*初始化腾讯Bugly应用分析上报功能*/
            if (isUseBugly && ObjectUtils.isNotEmpty(buglyID)) {
                // 实例化Bugly配置对象
                buglyConfig = new BuglyConfig(buglyID);
                // 重新设置Bugly配置对象
                buglyConfig = resetBuglyConfig(buglyConfig);
                // 初始化Bugly功能
                buglyConfig.initBugly(application.getApplicationContext());

                LogUtils.d(buglyConfig.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以重写此方法，直接修改入参对象然后return反馈即可。
     *
     * @param buglyConfig 原Bugly配置对象
     * @return 新Bugly配置对象
     */
    protected static BuglyConfig resetBuglyConfig(BuglyConfig buglyConfig) {
        return buglyConfig;
    }
}
