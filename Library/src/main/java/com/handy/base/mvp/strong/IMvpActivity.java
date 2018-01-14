package com.handy.base.mvp.strong;

/**
 * Created by LiuJie on 2016/9/28.
 */
public interface IMvpActivity<IFragment extends IMvpFragment> {
    void setFragment(IFragment iFragment);
}
