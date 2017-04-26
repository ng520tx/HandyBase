package com.handy.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Activity栈管理
 * </pre>
 */
public final class ActivityStackUtils {

    private volatile static ActivityStackUtils instance;
    private static Stack<Activity> activityStack; //Activity栈

    public static ActivityStackUtils getInstance() {
        if (instance == null) {
            synchronized (ActivityStackUtils.class) {
                if (instance == null) {
                    instance = new ActivityStackUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            return activity;
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (aty.getClass().equals(cls)) {
                    iterator.remove();
                    aty.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                iterator.remove();
                aty.finish();
            }
            activityStack.clear();
        }
    }

    /**
     * 结束第一个Activity之后的所有Activity
     */
    public void finishToFirstActivity() {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (activityStack.size() > 1) {
                    iterator.remove();
                    aty.finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public void setActivityStack(Stack<Activity> activityStack) {
        ActivityStackUtils.activityStack = activityStack;
    }
}
