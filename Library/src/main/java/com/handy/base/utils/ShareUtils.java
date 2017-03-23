package com.handy.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP相关工具类
 * </pre>
 */
public class ShareUtils {

    private volatile static ShareUtils instance;
    public String ShareName = "HandyBase_Share";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * 获取单例
     */
    public static ShareUtils getInstance() {
        if (instance == null) {
            synchronized (ShareUtils.class) {
                if (instance == null) {
                    instance = new ShareUtils();
                }
            }
        }
        return instance;
    }

    /**
     * ShareUtils初始化
     */
    private void initShareUtils() {
        sharedPreferences = HandyBaseUtils.getInstance().getContext().getSharedPreferences(ShareName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * sharedPreferences中写入String类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, @Nullable String value) {
        initShareUtils();
        editor.putString(key, value).apply();
    }

    /**
     * sharedPreferences中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * sharedPreferences中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        initShareUtils();
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * sharedPreferences中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, int value) {
        initShareUtils();
        editor.putInt(key, value).apply();
    }

    /**
     * sharedPreferences中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * sharedPreferences中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        initShareUtils();
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * sharedPreferences中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, long value) {
        initShareUtils();
        editor.putLong(key, value).apply();
    }

    /**
     * sharedPreferences中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * sharedPreferences中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        initShareUtils();
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * sharedPreferences中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, float value) {
        initShareUtils();
        editor.putFloat(key, value).apply();
    }

    /**
     * sharedPreferences中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * sharedPreferences中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        initShareUtils();
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * sharedPreferences中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, boolean value) {
        initShareUtils();
        editor.putBoolean(key, value).apply();
    }

    /**
     * sharedPreferences中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * sharedPreferences中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        initShareUtils();
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * sharedPreferences中写入String集合类型value
     *
     * @param key    键
     * @param values 值
     */
    public void put(String key, @Nullable Set<String> values) {
        initShareUtils();
        editor.putStringSet(key, values).apply();
    }

    /**
     * sharedPreferences中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    /**
     * sharedPreferences中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(String key, @Nullable Set<String> defaultValue) {
        initShareUtils();
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    /**
     * sharedPreferences中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        initShareUtils();
        return sharedPreferences.getAll();
    }

    /**
     * sharedPreferences中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        initShareUtils();
        editor.remove(key).apply();
    }

    /**
     * sharedPreferences中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(String key) {
        initShareUtils();
        return sharedPreferences.contains(key);
    }

    /**
     * sharedPreferences中清除所有数据
     */
    public void clear() {
        initShareUtils();
        editor.clear().apply();
    }
}