package com.handy.base.mvp;

/**
 * Created by LiuJie on 2016/9/5.
 */
public interface IMvpPresenter {

    /**
     * 第一次启动时加载
     */
    void onInitPresenter();

    /**
     * Activity销毁时结束所有执行中的任务
     */
    void onDestroy();
}
