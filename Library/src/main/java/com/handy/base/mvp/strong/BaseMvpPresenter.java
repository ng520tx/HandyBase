package com.handy.base.mvp.strong;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 类名
 *
 * @author LiuJie https://github.com/Handy045
 * @description 请描述类的实现或功能
 * @date Created in 2018/1/15 上午12:14
 * @modified By LiuJie
 */
public abstract class BaseMvpPresenter implements IMvpPresenter {
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onInitPresenter() {

    }

    @Override
    public void onDestroy() {
        unDispose();
        this.mCompositeDisposable = null;
    }

    /**
     * 将所有disposable放入,集中处理
     */
    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 保证activity结束时取消所有正在执行的订阅
     */
    private void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
