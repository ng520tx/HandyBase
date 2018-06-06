package com.handy.base.app;

import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * Created by LiuJie on 2017/4/19.
 */

public class SubActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.crashTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> strings = null;
                String str = strings.get(0);
            }
        });
    }
}
