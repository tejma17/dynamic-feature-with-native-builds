package com.tejma.dynamicfeatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

public class MainActivity extends AppCompatActivity {

    SplitInstallManager splitInstallManager;
    String TAG = "INSTALLFEATURE";
    int mySessionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splitInstallManager = SplitInstallManagerFactory.create(this);

        findViewById(R.id.install_btn).setOnClickListener(v->installModule());


        findViewById(R.id.open_activity_btn).setOnClickListener(v->{
            //first check if module is installed or not
            if(splitInstallManager.getInstalledModules().contains("dynamicfeature")) {
                startActivity(new Intent()
                        .setClassName(BuildConfig.APPLICATION_ID, "com.tejma.dynamicfeature.DynamicFeatureActivity")
                        .putExtra("ExtraInt", 3));
            }
        });
    }

    public void installModule(){

        // Creates a listener for request status updates.
        SplitInstallStateUpdatedListener listener = state -> {
            if (state.sessionId() == mySessionId) {
                switch (state.status()){
                    case SplitInstallSessionStatus.DOWNLOADING:
                        Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
                    case SplitInstallSessionStatus.INSTALLED:
                        Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show();
                    case SplitInstallSessionStatus.FAILED:
                        Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Registers the listener.
        splitInstallManager.registerListener(listener);


        // Creates a request to install a module.
        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        .addModule("dynamicfeature")      //this is the title of module we specified while creating it
                        .build();


        splitInstallManager
                .startInstall(request)
                .addOnSuccessListener(sessionId -> {
                    mySessionId = sessionId;
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Log.d(TAG, exception.getMessage());
                    Toast.makeText(this, "FAILURE", Toast.LENGTH_SHORT).show();
                });

        // When your app no longer requires further updates, unregister the listener.
        splitInstallManager.unregisterListener(listener);

    }
}