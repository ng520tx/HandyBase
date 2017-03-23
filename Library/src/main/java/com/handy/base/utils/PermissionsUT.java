package com.handy.base.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Android6.0权限处理工具类
 * <p/>
 * Created by LiuJie on 2016/7/20.
 */
public class PermissionsUT {

    private volatile static PermissionsUT instance;
    public List<String> Permissions = new ArrayList<>();

    /**
     * 获取单例
     */
    public static PermissionsUT getInstance() {
        if (instance == null) {
            synchronized (PermissionsUT.class) {
                if (instance == null) {
                    instance = new PermissionsUT();
                }
            }
        }
        return instance;
    }

    /**
     * 扫描权限集合 如果发现未启用权限，停止扫描并启动权限动态请求。
     * 使用时请注意判断SDK版本：if (Build.VERSION.SDK_INT >= 23)
     *
     * @param isRequest 是否发起系统权限请求
     * @return 如果有未启用的权限返回false
     */
    private boolean checkDeniedPermissionsBase(Activity activity, boolean isRequest) {
        for (int index = 0; index < Permissions.size(); index++) {
            if (ContextCompat.checkSelfPermission(activity, Permissions.get(index)) == PackageManager.PERMISSION_DENIED) {
                if (isRequest)
                    ActivityCompat.requestPermissions(activity, new String[]{Permissions.get(index)}, index);
                return true;
            }
        }
        return false;
    }

    private boolean checkDeniedPermissionsBase(Activity activity, boolean isRequest, List<String> permissions) {
        for (int index = 0; index < permissions.size(); index++) {
            if (ContextCompat.checkSelfPermission(activity, permissions.get(index)) == PackageManager.PERMISSION_DENIED) {
                if (isRequest)
                    ActivityCompat.requestPermissions(activity, new String[]{permissions.get(index)}, index);
                return true;
            }
        }
        return false;
    }

    /**
     * 扫描权限集合 如果发现未启用权限，停止扫描并启动权限动态请求。
     * 使用时请注意判断SDK版本：if (Build.VERSION.SDK_INT >= 23)
     *
     * @param activity  发起请求的Activity
     * @param isRequest 是否发起系统权限请求
     * @return 如果有未启用的权限返回true
     */
    public boolean checkDeniedPermissions(Activity activity, boolean isRequest) {
        if (Permissions.size() != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkDeniedPermissionsBase(activity, isRequest)) {
                    LogUtils.getInstance().w("发现未启用权限");
                    return true;
                } else {
                    LogUtils.getInstance().d("应用权限已全部开启");
                }
            } else {
                LogUtils.getInstance().d("系统版本小于23，无需扫描权限");
            }
        } else {
            LogUtils.getInstance().e("未发现需扫描的权限内容");
        }
        return false;
    }

    /**
     * 扫描权限集合 如果发现未启用权限，停止扫描并启动权限动态请求。
     * 使用时请注意判断SDK版本：if (Build.VERSION.SDK_INT >= 23)
     *
     * @param activity    发起请求的Activity
     * @param isRequest   是否发起系统权限请求
     * @param permissions 权限扫描的参数
     * @return 如果有未启用的权限返回true
     */
    public boolean checkDeniedPermissions(Activity activity, List<String> permissions, boolean isRequest) {
        if (permissions.size() != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkDeniedPermissionsBase(activity, isRequest, permissions)) {
                    LogUtils.getInstance().w("发现未启用权限");
                    return true;
                } else {
                    LogUtils.getInstance().d("应用权限已全部开启");
                }
            } else {
                LogUtils.getInstance().d("系统版本小于23，无需扫描权限");
            }
        } else {
            LogUtils.getInstance().e("未发现需扫描的权限内容");
        }
        return false;
    }

    /**
     * 扫描权限集合 如果发现未启用权限，停止扫描并启动权限动态请求。
     *
     * @param activity    发起请求的Activity
     * @param intentClass 扫描通过后被跳转的Class
     * @param isRequest   是否发起系统权限请求
     * @param isFinish    是否关闭当前Activity
     * @return 如果有未启用的权限返回true
     */
    public boolean checkDeniedPermissions(Activity activity, Class intentClass, boolean isRequest, boolean isFinish) {
        if (Permissions.size() != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkDeniedPermissionsBase(activity, isRequest)) {
                    LogUtils.getInstance().w("发现未启用权限");
                    return true;
                } else {
                    if (isFinish) activity.finish();
                    LogUtils.getInstance().d("应用权限已全部开启");
                    IntentUtils.getInstance().openActivity(activity, intentClass, isFinish);
                }
            } else {
                if (isFinish) activity.finish();
                LogUtils.getInstance().d("系统版本小于23，无需扫描权限");
                IntentUtils.getInstance().openActivity(activity, intentClass, isFinish);
            }
        } else {
            LogUtils.getInstance().e("未发现需扫描的权限内容");
        }
        return false;
    }

    /**
     * 扫描权限集合 如果发现未启用权限，停止扫描并启动权限动态请求。
     *
     * @param activity    发起请求的Activity
     * @param intentClass 扫描通过后被跳转的Class
     * @param isRequest   是否发起系统权限请求
     * @param isFinish    是否关闭当前Activity
     * @param permissions 权限扫描的参数
     * @return 如果有未启用的权限返回true
     */
    public boolean checkDeniedPermissions(Activity activity, Class intentClass, List<String> permissions, boolean isRequest, boolean isFinish) {
        if (permissions != null && permissions.size() != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkDeniedPermissionsBase(activity, isRequest, permissions)) {
                    LogUtils.getInstance().w("发现未启用权限");
                    return true;
                } else {
                    if (isFinish) activity.finish();
                    LogUtils.getInstance().d("应用权限已全部开启");
                    IntentUtils.getInstance().openActivity(activity, intentClass, isFinish);
                }
            } else {
                if (isFinish) activity.finish();
                LogUtils.getInstance().d("系统版本小于23，无需扫描权限");
                IntentUtils.getInstance().openActivity(activity, intentClass, isFinish);
            }
        } else {
            LogUtils.getInstance().e("未发现需扫描的权限内容");
        }
        return false;
    }
}
