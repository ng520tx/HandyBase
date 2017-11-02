package com.handy.base.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2016/09/23
 *  desc  : 意图相关工具类
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void openActivity(Activity activity, Class<?> clss, boolean isFinish) {
        Intent intent = new Intent(activity, clss);
        activity.startActivity(intent);
        if (isFinish)
            activity.finish();
    }

    public static void openActivity(Activity activity, Class<?> clss, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, clss);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        if (isFinish)
            activity.finish();
    }

    public static void openActivityForResult(Activity activity, Class<?> clss, int requestCode, boolean isFinish) {
        Intent intent = new Intent(activity, clss);
        activity.startActivityForResult(intent, requestCode);
        if (isFinish)
            activity.finish();
    }

    public static void openActivityForResult(Activity activity, Class<?> clss, Bundle bundle, int requestCode, boolean isFinish) {
        Intent intent = new Intent(activity, clss);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
        if (isFinish)
            activity.finish();
    }
}
