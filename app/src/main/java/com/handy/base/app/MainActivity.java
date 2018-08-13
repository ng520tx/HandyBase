package com.handy.base.app;

import android.os.Bundle;
import android.view.View;

import com.handy.base.HandyBase;
import com.handy.base.utils.IntentUtils;
import com.handy.base.utils.androidutilcode.CoordinateUtils;

public class MainActivity extends BaseActivity {

    public static void main(String[] args) {
        System.out.println("wgs84ToBd09: " + CoordinateUtils.wgs84ToBd09(117.0465743849, 38.9439192039)[1] + "," + CoordinateUtils.wgs84ToBd09(117.0465743849, 38.9439192039)[0]);
        System.out.println("bd09ToWGS84: " + CoordinateUtils.bd09ToWGS84(117.0594433713, 38.9506451571)[1] + "," + CoordinateUtils.bd09ToWGS84(117.0594433713, 38.9506451571)[0]);
        System.out.println("\n");
        System.out.println("gcj02ToBd09: " + CoordinateUtils.gcj02ToBd09(117.0528459549, 38.9448994339)[1] + "," + CoordinateUtils.gcj02ToBd09(117.0528459549, 38.9448994339)[0]);
        System.out.println("bd09ToGcj02: " + CoordinateUtils.bd09ToGcj02(117.0594433713, 38.9506451571)[1] + "," + CoordinateUtils.bd09ToGcj02(117.0594433713, 38.9506451571)[0]);
        System.out.println("\n");
        System.out.println("gcj02ToWGS84: " + CoordinateUtils.gcj02ToWGS84(117.0528459549, 38.9448994339)[1] + "," + CoordinateUtils.gcj02ToWGS84(117.0528459549, 38.9448994339)[0]);
        System.out.println("wgs84ToGcj02: " + CoordinateUtils.wgs84ToGcj02(117.0465743849, 38.9439192039)[1] + "," + CoordinateUtils.wgs84ToGcj02(117.0465743849, 38.9439192039)[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandyBase.buglyID = "dda0e5e9a5";
        HandyBase.getInstance().init(getApplication());

        findViewById(R.id.crashTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openActivity(MainActivity.this, SubActivity.class, false);
            }
        });
    }
}
