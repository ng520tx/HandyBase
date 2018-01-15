package com.handy.base.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.disposables.Disposable;

/**
 * 类名
 *
 * @author LiuJie https://github.com/Handy045
 * @description 请描述类的实现或功能
 * @date Created in 2018/1/15 上午10:55
 * @modified By LiuJie
 */
public class BaseMvpContract {

    public interface IMvpView {

        /**
         * 绑定生命周期
         */
        <T> LifecycleTransformer<T> bindToLife();
    }

    public interface IMvpPresenter<V extends IMvpView> {
        /**
         * 绑定View
         */
        void attachView(V iMvpView);

        /**
         * 解除View
         */
        void detachView();

        /**
         * 添加订阅，将所有disposable放入,集中处理
         */
        void addDispose(Disposable disposable);

        /**
         * 解除订阅，保证activity结束时取消所有正在执行的订阅
         */
        void unDispose();

        /**
         * Activity销毁时结束所有执行中的任务
         */
        void onDestroy();
    }
}
