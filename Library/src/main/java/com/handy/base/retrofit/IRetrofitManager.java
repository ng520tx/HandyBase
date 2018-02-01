package com.handy.base.retrofit;

import android.content.Context;

import com.handy.base.mvp.IModel;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 *
 * @author LiuJie https://github.com/Handy045
 * @description 提供给 {@link IModel} 必要的 Api 做数据处理
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface IRetrofitManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cache);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    Context getContext();
}
