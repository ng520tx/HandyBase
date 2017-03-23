package com.handy.base.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/25
 *     desc  : 剪贴板相关工具类
 * </pre>
 */
public class ClipboardUtils {

    private volatile static ClipboardUtils instance;

    /**
     * 获取单例
     */
    public static ClipboardUtils getInstance() {
        if (instance == null) {
            synchronized (ClipboardUtils.class) {
                if (instance == null) {
                    instance = new ClipboardUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public void copyText(CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public CharSequence getText() {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(HandyBaseUtils.getContext());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public void copyUri(Uri uri) {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newUri(HandyBaseUtils.getContext().getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public Uri getUri() {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public void copyIntent(Intent intent) {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public Intent getIntent() {
        ClipboardManager clipboard = (ClipboardManager) HandyBaseUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
