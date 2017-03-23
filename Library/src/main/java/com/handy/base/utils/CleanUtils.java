package com.handy.base.utils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : 清除相关工具类
 * </pre>
 */
public class CleanUtils {

    private volatile static CleanUtils instance;

    /**
     * 获取单例
     */
    public static CleanUtils getInstance() {
        if (instance == null) {
            synchronized (CleanUtils.class) {
                if (instance == null) {
                    instance = new CleanUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalCache() {
        return FileUtils.getInstance().deleteFilesInDir(HandyBaseUtils.getInstance().getContext().getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalFiles() {
        return FileUtils.getInstance().deleteFilesInDir(HandyBaseUtils.getInstance().getContext().getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalDbs() {
        return FileUtils.getInstance().deleteFilesInDir(HandyBaseUtils.getInstance().getContext().getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalDbByName(String dbName) {
        return HandyBaseUtils.getInstance().getContext().deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalSP() {
        return FileUtils.getInstance().deleteFilesInDir(HandyBaseUtils.getInstance().getContext().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanExternalCache() {
        return SDCardUtils.getInstance().isSDCardEnable() && FileUtils.getInstance().deleteFilesInDir(HandyBaseUtils.getInstance().getContext().getExternalCacheDir());
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanCustomCache(String dirPath) {
        return FileUtils.getInstance().deleteFilesInDir(dirPath);
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanCustomCache(File dir) {
        return FileUtils.getInstance().deleteFilesInDir(dir);
    }
}
