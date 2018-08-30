package com.handy.base.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.CrashUtils;
import com.handy.base.access.HandyBase;
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
        HandyBase.getInstance()
                .setBuglyID("dda0e5e9a5")
                .setOnCrashListener(new CrashUtils.OnCrashListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onCrash(String crashInfo, Throwable e) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                Looper.prepare();
                                Toast.makeText(context, "很抱歉：程序出现异常即将退出", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                return null;
                            }
                        }.execute();

                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);

                        SystemClock.sleep(1000L);

                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .init(getApplication());

        findViewById(R.id.crashTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.openActivity(MainActivity.this, SubActivity.class, false);
            }
        });
    }
}
