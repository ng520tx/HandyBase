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
public class UserModule implements UserContract.userModel {

    private UserContract.userView view;

    public UserModule(UserContract.userView view) {
        this.view = view;
    }

    @Provides
    public UserContract.userView providesView() {
        return view;
    }

    @Provides
    public UserContract.userModel providesModel(UserContract.userView userView) {
        return new UserModel(userView);
    }

    @Provides
    public UserContract.userPresenter providesPresenter(UserContract.userModel model, UserContract.userView view) {
        return new UserPresenter(model, view);
    }

    @Override
    public void onDestroy() {

    }
}
