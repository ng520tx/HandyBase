package com.handy.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : 崩溃相关工具类
 * </pre>
 */
public final class CrashUtils implements UncaughtExceptionHandler {

    private volatile static CrashUtils instance;

    private Context context;
    private int versionCode;
    private String crashDir;
    private String versionName;
    private boolean mInitialized;
    private UncaughtExceptionHandler mHandler;

    private CrashUtils() {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        if (instance == null) {
            synchronized (CrashUtils.class) {
                if (instance == null) {
                    instance = new CrashUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init(Context context) {
        this.context = context;
        if (mInitialized) return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File baseCache = context.getExternalCacheDir();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        } else {
            File baseCache = context.getCacheDir();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                LogUtils.getInstance().e(Log.getStackTraceString(throwable));
                Toast.makeText(context, "很抱歉：程序出现异常即将退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        final String fullPath = crashDir + now + ".txt";
        if (!createOrExistsFile(fullPath)) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(fullPath, false));
                    pw.write(getCrashHead());
                    throwable.printStackTrace(pw);
                    Throwable cause = throwable.getCause();
                    while (cause != null) {
                        cause.printStackTrace(pw);
                        cause = cause.getCause();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.getInstance().closeIO(pw);
                }
            }
        }).start();

        try {
            Thread.sleep(2000);
            //退出程序
            ActivityStackUtils.getInstance().finishAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private boolean createOrExistsFile(String filePath) {
        File file = new File(filePath);
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
