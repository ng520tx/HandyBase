package com.handy.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.handy.base.app.dagger.UserActivity;
import com.handy.base.utils.IntentUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        findViewById(R.id.crashTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openActivity(activity, UserActivity.class, false);
            }
        });
    }
}
