package com.handy.base.mvp.strong;

/**
 * Created by LiuJie on 2016/9/5.
 */
public interface IMvpFragment<IActivity extends IMvpActivity, IPresenter extends IMvpPresenter> {
    void setActivity(IActivity iActivity);

    void setPresenter(IPresenter presenterApi);

}
