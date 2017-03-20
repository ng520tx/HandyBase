package com.handy.base.strongmvp;

/**
 * Created by LiuJie on 2016/9/5.
 */
public interface BaseMvpView<A, P> {
    void setActivity(A activityApi);

    void setPresenter(P presenterApi);
}
