package com.tejma.dynamicfeature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tejma.nativecapp.NativeMainActivity;

public class DynamicFeatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_feature);

        findViewById(R.id.open_library_btn).setOnClickListener(v->{
            startActivity(new Intent(DynamicFeatureActivity.this,
                    NativeMainActivity.class));
        });
    }
}