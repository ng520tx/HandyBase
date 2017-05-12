package com.handy.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Collections;
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

    private static Stack<Activity> activityStack; //Activity栈

    private ActivityStackUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity getCurrent() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            return activity;
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finish() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            finishChoiceDesc(activity);
        }
    }

    /**
     * 按顺序单次结束指定的Activity
     */
    public static void finishChoiceAsc(Activity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) activity.finish();
        }
    }

    /**
     * 按倒叙单次结束指定的Activity
     */
    public static void finishChoiceDesc(Activity activity) {
        if (activity != null && activityStack != null) {
            Collections.reverse(activityStack); // 倒序排列
            activityStack.remove(activity);
            if (!activity.isFinishing()) activity.finish();
            Collections.reverse(activityStack); // 倒序排列
        }
    }

    /**
     * 结束全部指定的Activit
     */
    public static void finishChoices(Activity activity) {
        if (activity != null && activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (aty.getClass().equals(activity.getClass())) {
                    iterator.remove();
                    if (!aty.isFinishing()) aty.finish();
                }
            }
        }
    }

    /**
     * 按顺序单次结束指定的Activity
     */
    public static void finishChoiceAsc(Class<?> cls) {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (aty.getClass().equals(cls)) {
                    iterator.remove();
                    if (!aty.isFinishing()) aty.finish();
                    break;
                }
            }
        }
    }

    /**
     * 按倒叙单次结束指定的Activity
     */
    public static void finishChoiceDesc(Class<?> cls) {
        if (activityStack != null) {
            Collections.reverse(activityStack); // 倒序排列
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (aty.getClass().equals(cls)) {
                    iterator.remove();
                    if (!aty.isFinishing()) aty.finish();
                    break;
                }
            }
            Collections.reverse(activityStack); // 倒序排列
        }
    }

    /**
     * 结束全部指定的Activit
     */
    public static void finishChoices(Class<?> cls) {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (aty.getClass().equals(cls)) {
                    iterator.remove();
                    if (!aty.isFinishing()) aty.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAll() {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                iterator.remove();
                if (!aty.isFinishing()) aty.finish();
            }
            activityStack.clear();
        }
    }

    /**
     * 结束第一个Activity之后的所有Activity
     */
    public static void finish2First() {
        if (activityStack != null) {
            Iterator iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity aty = (Activity) iterator.next();
                if (activityStack.size() > 1) {
                    iterator.remove();
                    if (!aty.isFinishing()) aty.finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAll();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static void setActivityStack(Stack<Activity> activityStack) {
        ActivityStackUtils.activityStack = activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }
}
