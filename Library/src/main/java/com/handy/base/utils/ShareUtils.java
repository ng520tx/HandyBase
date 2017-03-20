package com.handy.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP轻量级存储工具类
 * <p>
 * Created by LiuJie on 2016/1/19.
 */
public class ShareUtils {

    private static ShareUtils shareUtils = null;
    private String SP_NAME = "LIUJIE045.sp";

    private ShareUtils() {
    }

    public synchronized static ShareUtils getInstance() {
        if (shareUtils == null) {
            shareUtils = new ShareUtils();
        }
        return shareUtils;
    }

    public String getSP_NAME() {
        return SP_NAME;
    }

    public void setSP_NAME(String SP_NAME) {
        this.SP_NAME = SP_NAME;
    }

    /**
     * 保存在手机里面的文件名
     */

    public void setStringParam(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 保存字符串数据类型
     *
     * @param context
     * @param keys
     * @param values
     */
    public void setStringParams(Context context, String[] keys, String[] values) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for (int i = 0; i < keys.length; i++) {
            edit.putString(keys[i], values[i]);
        }
        edit.commit();
    }

    /**
     * 保存所有数据类型 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void setParam(Context context, String key, Object object) {
        try {
            String type = object.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            key = AesUtils.getInstance().encrypt(key);
            if (key != null && !key.equals("")) {
                if ("String".equals(type)) {
                    editor.putString(key, AesUtils.getInstance().encrypt((String) object));
                } else if ("Integer".equals(type)) {
                    editor.putInt(key, (Integer) object);
                } else if ("Boolean".equals(type)) {
                    editor.putBoolean(key, (Boolean) object);
                } else if ("Float".equals(type)) {
                    editor.putFloat(key, (Float) object);
                } else if ("Long".equals(type)) {
                    editor.putLong(key, (Long) object);
                }
            }
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(Context context, String key, Object defaultObject) {
        try {
            String type = defaultObject.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            key = AesUtils.getInstance().encrypt(key);
            if (key != null && !key.equals("")) {
                if ("String".equals(type)) {
                    return AesUtils.getInstance().decrypt(sp.getString(key, (String) defaultObject));
                } else if ("Integer".equals(type)) {
                    return sp.getInt(key, (Integer) defaultObject);
                } else if ("Boolean".equals(type)) {
                    return sp.getBoolean(key, (Boolean) defaultObject);
                } else if ("Float".equals(type)) {
                    return sp.getFloat(key, (Float) defaultObject);
                } else if ("Long".equals(type)) {
                    return sp.getLong(key, (Long) defaultObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultObject;
    }
}
