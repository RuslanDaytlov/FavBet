package com.sport.sport;

import android.app.Application;

import com.onesignal.OneSignal;


public class MaySignal extends Application {


    private static final String ONESIGNAL_APP_ID = "a83456ff-b1ef-4815-9b0a-499086fc1450;";


    @Override
    public void onCreate() {
        super.onCreate();


        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

       
    }
}
