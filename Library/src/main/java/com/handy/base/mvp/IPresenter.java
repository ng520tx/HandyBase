package com.handy.base.mvp;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Mvp框架中 Presenter层通用接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Presenter层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 向Presenter添加Model，用以批量绑定、解绑等
     *
     * @param baseModel
     */
    void addModel(BaseModel baseModel);

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();

    /**
     * 将RxJava任务加入集合统一管理生命周期<br>
     * 在IPresenter的实现类中创建全局变量 CompositeDisposable。<br>
     * 将 {@link Observer#onSubscribe(Disposable)} 方法中的 {@link Disposable} 添加到全局变量 CompositeDisposable 中统一管理。<br>
     * 具体实现内容：
     * <pre>
     * if (mCompositeDisposable == null) {
     *     mCompositeDisposable = new CompositeDisposable();
     * }
     * //将所有 Disposable 放入集中处理
     * mCompositeDisposable.add(disposable);
     * </pre>
     *
     * @param disposable
     */
    void addDispose(@NonNull Disposable disposable);

    /**
     * 停止集合中正在执行的RxJava任务<br>
     * 在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏<br>
     * 目前框架已使用 {@link RxLifecycle} 避免内存泄漏,此方法作为备用方案<br>
     * 具体实现内容：
     * <pre>
     * if (mCompositeDisposable != null) {
     *     mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
     * }
     * </pre>
     */
    void unDispose();
}
