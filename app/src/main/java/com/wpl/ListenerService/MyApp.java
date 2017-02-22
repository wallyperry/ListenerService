package com.wpl.ListenerService;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Application
 * Created by Administrator on 2017/2/22.
 */

public class MyApp extends Application {

    public static final String APP_KEY = "d6fecacfa03937555cb24ea43881193f";

    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }

    private void init() {
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId(APP_KEY)
                .setConnectTimeout(60 * 5)
                .build();
        Bmob.initialize(config);
    }
}
