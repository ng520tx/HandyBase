package com.handy.base.app;

import android.app.Application;

import com.handy.base.utils.AppUtils;
import com.handy.base.utils.CrashUtils;
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
                CrashUtils.getInstance().init(getApplicationContext()); //初始化崩溃捕获工具

                LogUtils.getInstance().initBuilder(getApplicationContext())
                        .setLogSwitch(AppUtils.getInstance().isAppDebug(getApplicationContext()))// 设置log总开关，默认开
                        .setGlobalTag("HandyBase")// 设置log全局标签，默认为空，当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
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

    /*
     * 如果要使用蒲公英内测功能，需要在AndroidManifest配置文件中加入：
     * <application>
     *     ...
     *     <provider
     *         android:name="android.support.v4.content.FileProvider"
     *         android:authorities="此处输入授权名，可以随意输入但要保证唯一性，可能会和手机其他App产生冲突"
     *         android:exported="false"
     *         android:grantUriPermissions="true">
     *         <meta-data
     *             android:name="android.support.FILE_PROVIDER_PATHS"
     *             android:resource="@xml/pgycrash_paths"/>
     *     </provider>
     * </application>
     *
     * 然后再res下的xml文件夹（不存在则创建）中创建pgycrash_paths.xml文件，写入内容：
     * <?xml version="1.0" encoding="utf-8"?>
     * <paths>
     *     <external-path
     *         name="files_root"
     *         path="Android/data/com/pgyersdk"/>
     *     <external-path
     *         name="external_storage_root"
     *         path="."/>
     * </paths>
     */
}
