package com.handy.base.mvp;

import android.app.Activity;

/**
 * Mvp框架中 View层通用接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有View层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface IView {

    /**
     * View标识信息，可用于Log日志的Tag等。<br>
     * 建议使用：<实现类>.class.getSimpleName()方法。
     */
    String getViewTag();

    /**
     * 杀死自己，用于注销事务
     */
    void killMyself();

    /**
     * 通过接口获取Activity
     */
    Activity getActivity();
}
