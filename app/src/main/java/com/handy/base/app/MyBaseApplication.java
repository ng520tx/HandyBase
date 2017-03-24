package com.handy.base.app;

import android.Manifest;

import com.handy.base.utils.PermissionsUtils;

import java.util.ArrayList;

/**
 * Created by LiuJie on 2017/3/24.
 */

public class MyBaseApplication extends BaseApplication {
    {
        PermissionsUtils.getInstance().Permissions = new ArrayList<String>() {{
            add(Manifest.permission.INTERNET);
            add(Manifest.permission.READ_PHONE_STATE);
            add(Manifest.permission.ACCESS_WIFI_STATE);
            add(Manifest.permission.ACCESS_NETWORK_STATE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }};
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2017/3/24 手动初始化工具类
    }
}
