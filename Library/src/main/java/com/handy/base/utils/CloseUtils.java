package com.handy.base.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/9
 *     desc  : 关闭相关工具类
 * </pre>
 */
public class CloseUtils {

    private volatile static CloseUtils instance;

    /**
     * 获取单例
     */
    public static CloseUtils getInstance() {
        if (instance == null) {
            synchronized (CloseUtils.class) {
                if (instance == null) {
                    instance = new CloseUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    public void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
