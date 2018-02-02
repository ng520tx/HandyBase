package com.handy.base.app.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/2 上午1:36
 * @modified By LiuJie
 */
@Module
public class UserModel implements UserContract.userModel {

    private UserActivity userActivity;
    private UserContract.userPresenter presenter;

    public UserModel(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    @Provides
    public UserContract.userPresenter providesPresenter() {
        return new UserPresenter(this, userActivity);
    }

    @Override
    public void onDestroy() {

    }
}
