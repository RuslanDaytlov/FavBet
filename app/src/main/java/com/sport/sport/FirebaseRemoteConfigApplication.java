package com.sport.sport;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.favbet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;




public class FirebaseRemoteConfigApplication extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";

    private static FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static FirebaseRemoteConfigApplication mInstance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(FirebaseRemoteConfigApplication.this, "Fetch and activate succeeded", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(FirebaseRemoteConfigApplication.this, "Fetch failed", Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });

        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(ConfigUpdate configUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.getUpdatedKeys());

                if (configUpdate.getUpdatedKeys().contains("welcome_message")) {
                    mFirebaseRemoteConfig.activate()
                            .addOnCompleteListener(task -> displayWelcomeMessage());
                }
            }

            @Override
            public void onError(@NonNull FirebaseRemoteConfigException error) {

            }

        });
    }

    private void displayWelcomeMessage() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);

    }


    public static synchronized FirebaseRemoteConfigApplication getInstance() {
        return mInstance;
    }

    public static synchronized FirebaseRemoteConfig getFirebaseInstance() {
        return mFirebaseRemoteConfig;
    }
}

