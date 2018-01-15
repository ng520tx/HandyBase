package com.handy.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 类名
 *
 * @author LiuJie https://github.com/Handy045
 * @description 请描述类的实现或功能
 * @date Created in 2018/1/15 上午10:42
 * @modified By LiuJie
 */
public class BaseMvpPresenter<V extends BaseMvpContract.IMvpView> implements BaseMvpContract.IMvpPresenter<V> {

    protected V iMvpView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(V iMvpView) {
        this.iMvpView = iMvpView;
    }

    @Override
    public boolean isAttached() {
        return iMvpView != null;
    }

    @Override
    public void detachView() {
        if (iMvpView != null) {
            iMvpView = null;
        }
    }

    @Override
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        detachView();
        unDispose();
    }
}
