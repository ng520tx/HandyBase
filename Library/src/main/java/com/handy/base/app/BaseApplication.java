package com.handy.base.app;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.base.config.BuglyConfig;
import com.handy.base.utils.androidutilcode.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Application基本类
 * </pre>
 */
public abstract class BaseApplication extends Application {

    public LogUtils.Config config;

    public String buglyID = "";
    public BuglyConfig buglyConfig = null;

    public boolean isUseBugly = true;
    public boolean isInitLogUtils = true;
    public boolean isUseCuntomCrashUtil = true;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            /*初始化工具类*/
            Utils.init(this);
            com.blankj.utilcode.util.Utils.init(this);
            /*初始化侧滑返回功能*/
            BGASwipeBackHelper.init(this, null);
            /*初始化数据库*/
            FlowManager.init(getApplicationContext());

             /* 初始化崩溃捕获工具 */
            if (isUseCuntomCrashUtil) {
                com.handy.base.utils.CrashUtils.init();
            } else {
                com.blankj.utilcode.util.CrashUtils.init();
            }

            /* 初始化日志工具类功能 */
            if (isInitLogUtils) {
                config = LogUtils.getConfig()
                        // 设置log总开关，包括输出到控制台和文件，默认开
                        .setLogSwitch(AppUtils.isAppDebug())
                        // 设置是否输出到控制台开关，默认开
                        .setConsoleSwitch(AppUtils.isAppDebug())
                        // 设置log全局标签，默认为空。当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
                        .setGlobalTag("HandyBase")
                        // 设置log头信息开关，默认为开
                        .setLogHeadSwitch(false)
                        // 打印log时是否存到文件的开关，默认关
                        .setLog2FileSwitch(false)
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

            /* 初始化腾讯Bugly应用分析上报功能 */
            if (isUseBugly && ObjectUtils.isNotEmpty(buglyID)) {
                // 实例化Bugly配置对象
                buglyConfig = new BuglyConfig(buglyID);
                // 重新设置Bugly配置对象
                buglyConfig = resetBuglyConfig(buglyConfig);
                // 初始化Bugly功能
                buglyConfig.initBugly(getApplicationContext());

                LogUtils.d(buglyConfig.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以在baseApplication的子类重写此方法，直接修改入参对象然后return反馈即可。
     *
     * @param buglyConfig 原Bugly配置对象
     * @return 新Bugly配置对象
     */
    protected BuglyConfig resetBuglyConfig(BuglyConfig buglyConfig) {
        return buglyConfig;
    }
}
