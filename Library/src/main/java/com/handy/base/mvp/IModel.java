package com.handy.base.mvp;

/**
 * Mvp框架中 Model层通用接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有PModel层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface IModel {

    /**
     * 在MVP框架中，执行 {@link BasePresenter#onDestroy()} 方法时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}
