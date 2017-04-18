package com.handy.base.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : 关闭相关工具类
 * </pre>
 */
public final class CloseUtils {

    private volatile static CloseUtils instance;

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
