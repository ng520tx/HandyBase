package com.handy.base.app.dagger;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.handy.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/2 上午1:37
 * @modified By LiuJie
 */
public class UserPresenter extends BasePresenter<UserModel, UserActivity> implements UserContract.userPresenter {

    @Inject
    public UserPresenter(@NonNull UserModel model, @NonNull UserActivity view) {
        super(model, view);
        LogUtils.d("UserPresenter is Created");
    }
}
