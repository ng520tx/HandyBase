package com.handy.base.config;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.base.utils.ProcessUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯Bugly配置对象
 *
 * @author LiuJie https://github.com/Handy045
 * @description 包括UserStrategy、项目BuglyID、是否开启Debug模式等
 * @date Created in 2018/1/23 下午2:02
 * @modified By LiuJie
 */
public class BuglyConfig {
    /**
     * 是否启用Debug模式，默认：false-不启用
     */
    private boolean isDebug = false;
    /**
     * 设置是否只在主进程上报，默认：true-是
     */
    private boolean isUploadProcess = false;
    /**
     * 是否将App运行的设备注册为测试设备，默认：AppUtils.isAppDebug()
     */
    private boolean isDevelopmentDevice = false;
    /**
     * Bugly注册的产品ID
     */
    private String buglyID = "";
    /**
     * 设置渠道，默认：AppUtils.getAppName() + "-" + (AppUtils.isAppDebug() ? "Debug" : "Release")
     */
    private String appChannel = "";
    /**
     * App的版本名称，默认：AppUtils.getAppVersionName()
     */
    private String appVersion = "";
    /**
     * App的包名，默认：AppUtils.getAppPackageName()
     */
    private String appPackageName = "";
    /**
     * 发生Crash时，一起上报的一些自定义的环境信息，最多9组，默认：new HashMap<>(9)-已实例化但无内容
     */
    private HashMap<String, String> crashAddInfo = new HashMap<>(9);
    /**
     * 发生Crash时，一起上报的一些附加的跟踪数据信息，默认：new HashMap<>()-已实例化但无内容
     */
    private HashMap<String, String> crashFollowInfo = new HashMap<>();

    /**
     * 实例化Bugly配置对象，由于会用到AppUtils工具类，请务必保证Application执行完父类的onCreate方法后在实例化此对象
     *
     * @param buglyID Bugly注册的产品ID
     */
    public BuglyConfig(String buglyID) {
        this.isDebug = false;
        this.isUploadProcess = true;
        this.isDevelopmentDevice = AppUtils.isAppDebug();
        this.buglyID = buglyID;
        this.appChannel = AppUtils.getAppName() + "-" + (AppUtils.isAppDebug() ? "Debug" : "Release");
        this.appVersion = AppUtils.getAppVersionName();
        this.appPackageName = AppUtils.getAppPackageName();
        this.crashAddInfo = new HashMap<>(9);
        this.crashFollowInfo = new HashMap<>();
    }

    /**
     * 初始化Bugly
     *
     * @param appLicationContext ApplicationContext 上下文
     */
    public void initBugly(Context appLicationContext) {
        for (Map.Entry<String, String> entry : this.crashAddInfo.entrySet()) {
            if (ObjectUtils.isNotEmpty(entry.getKey()) && ObjectUtils.isNotEmpty(entry.getValue())) {
                // 发生Crash时的自定义的环境信息
                CrashReport.putUserData(appLicationContext, entry.getKey(), entry.getValue());
            }
        }
        //是否将App运行的设备注册为测试设备
        CrashReport.setIsDevelopmentDevice(appLicationContext, this.isDevelopmentDevice);
        Bugly.init(appLicationContext, this.buglyID, this.isDebug, this.getUserStrategy(appLicationContext));
    }

    /**
     * 根据配置内容，获取Bugly初始化所需的配置对象
     *
     * @param appLicationContext ApplicationContext 上下文
     * @return Bugly初始化所需的配置对象
     */
    private CrashReport.UserStrategy getUserStrategy(Context appLicationContext) {
        Context appContext = appLicationContext.getApplicationContext();
        // 获取当前包名
        String packageName = appContext.getPackageName();
        // 获取当前进程名
        String processName = ProcessUtils.getProcessName(android.os.Process.myPid());
        // 设置是否只在主进程上报
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(appContext);
        if (isUploadProcess) {
            userStrategy.setUploadProcess(processName == null || processName.equals(packageName));
        }
        // 设置渠道
        userStrategy.setAppChannel(appChannel);
        // App的版本
        userStrategy.setAppVersion(appVersion);
        // App的包名
        userStrategy.setAppPackageName(appPackageName);
        // 发生Crash时，一起上报的附加的跟踪数据信息
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    if (ObjectUtils.isNotEmpty(crashFollowInfo)) {
                        return crashFollowInfo;
                    } else {
                        return super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
                    }
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    if (ObjectUtils.isNotEmpty(crashFollowInfo)) {
                        return "Extra data.".getBytes("UTF-8");
                    } else {
                        return super.onCrashHandleStart2GetExtraDatas(crashType, errorType, errorMessage, errorStack);
                    }
                } catch (Exception e) {
                    return null;
                }
            }
        });
        return userStrategy;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public BuglyConfig setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public boolean isUploadProcess() {
        return isUploadProcess;
    }

    public BuglyConfig setUploadProcess(boolean uploadProcess) {
        isUploadProcess = uploadProcess;
        return this;
    }

    public boolean isDevelopmentDevice() {
        return isDevelopmentDevice;
    }

    public BuglyConfig setDevelopmentDevice(boolean developmentDevice) {
        isDevelopmentDevice = developmentDevice;
        return this;
    }

    public String getBuglyID() {
        return buglyID;
    }

    public BuglyConfig setBuglyID(String buglyID) {
        this.buglyID = buglyID;
        return this;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public BuglyConfig setAppChannel(String appChannel) {
        this.appChannel = appChannel;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public BuglyConfig setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public BuglyConfig setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
        return this;
    }

    public HashMap<String, String> getCrashAddInfo() {
        return crashAddInfo;
    }

    public BuglyConfig setCrashAddInfo(HashMap<String, String> crashAddInfo) {
        this.crashAddInfo = crashAddInfo;
        return this;
    }

    public HashMap<String, String> getCrashFollowInfo() {
        return crashFollowInfo;
    }

    public BuglyConfig setCrashFollowInfo(HashMap<String, String> crashFollowInfo) {
        this.crashFollowInfo = crashFollowInfo;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("======================= Bugly配置信息 =======================\n");
        stringBuilder.append("| 是否启用Debug模式：  【").append(isDebug).append("】\n");
        stringBuilder.append("| 设置是否只在主进程上报：  【").append(isUploadProcess).append("】\n");
        stringBuilder.append("| 是否将App运行的设备注册为测试设备：  【").append(isDevelopmentDevice).append("】\n");
        stringBuilder.append("| Bugly注册的产品ID：  【").append(buglyID).append("】\n");
        stringBuilder.append("| 设置渠道：  【").append(appChannel).append("】\n");
        stringBuilder.append("| App的版本名称：  【").append(appVersion).append("】\n");
        stringBuilder.append("| App的包名：  【").append(appPackageName).append("】\n");
        stringBuilder.append("| 发生Crash时，一起上报的自定义的环境信息：");

        if (ObjectUtils.isNotEmpty(crashFollowInfo)) {
            stringBuilder.append("\n");
            for (Map.Entry<String, String> entry : this.crashAddInfo.entrySet()) {
                if (ObjectUtils.isNotEmpty(entry.getKey()) && ObjectUtils.isNotEmpty(entry.getValue())) {
                    stringBuilder.append("| \t").append("Key：").append(entry.getKey()).append("Value：").append(entry.getValue()).append("\n");
                }
            }
        } else {
            stringBuilder.append("  【暂无").append("】\n");
        }

        stringBuilder.append("| 发生Crash时，一起上报的附加的跟踪数据信息：");
        if (ObjectUtils.isNotEmpty(crashFollowInfo)) {
            stringBuilder.append("\n");
            for (Map.Entry<String, String> entry : this.crashFollowInfo.entrySet()) {
                if (ObjectUtils.isNotEmpty(entry.getKey()) && ObjectUtils.isNotEmpty(entry.getValue())) {
                    stringBuilder.append("| \t").append("Key：").append(entry.getKey()).append("Value：").append(entry.getValue()).append("\n");
                }
            }
        } else {
            stringBuilder.append("  【暂无").append("】\n");
        }

        stringBuilder.append("============================================================");
        return stringBuilder.toString();
    }
}
