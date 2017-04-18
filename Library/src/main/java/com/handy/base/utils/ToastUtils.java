package com.handy.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {

    private volatile static ToastUtils instance;
    private Toast toast;
    private boolean isJumpWhenMore = true;
    private Handler sHandler = new Handler(Looper.getMainLooper());

    public static ToastUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public void init(boolean isJumpWhenMore) {
        this.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public void showShortToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public void showShortToastSafe(final Context context, final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public void showShortToastSafe(final Context context, final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public void showShortToastSafe(final Context context, final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public void showLongToastSafe(final Context context, final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public void showLongToastSafe(final Context context, final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public void showLongToastSafe(final Context context, final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public void showLongToastSafe(final Context context, final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public void showShortToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public void showShortToast(Context context, @StringRes int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public void showShortToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public void showLongToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public void showLongToast(Context context, @StringRes int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public void showLongToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private void showToast(Context context, @StringRes int resId, int duration) {
        showToast(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private void showToast(Context context, @StringRes int resId, int duration, Object... args) {
        showToast(context, String.format(context.getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private void showToast(Context context, String format, int duration, Object... args) {
        showToast(context, String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private void showToast(Context context, CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 取消吐司显示
     */
    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}