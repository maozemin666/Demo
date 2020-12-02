package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.haikang.HikConfig;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_device;
    private Button save;
    private EditText ip;
    private EditText AK;
    private EditText SK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_device = findViewById(R.id.button_device);
        save = findViewById(R.id.save);
        ip = findViewById(R.id.ip);
        AK = findViewById(R.id.AK);
        SK = findViewById(R.id.SK);
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("HiKang", Context.MODE_PRIVATE);
        ip.setText(sp.getString("ipText", ""));
        AK.setText(sp.getString("akText", ""));
        SK.setText(sp.getString("skText", ""));
        save.setOnClickListener(v -> {
            String ipText = ip.getText().toString().trim();
            String akText = AK.getText().toString().trim();
            String skText = SK.getText().toString().trim();
            sp.edit().putString("ipText", ipText).apply();
            sp.edit().putString("akText", akText).apply();
            sp.edit().putString("skText", skText).apply();
        });

        button_device.setOnClickListener(v -> {
            String ipText = ip.getText().toString().trim();
            String akText = AK.getText().toString().trim();
            String skText = SK.getText().toString().trim();
            if (TextUtils.isEmpty(ipText) || TextUtils.isEmpty(akText) || TextUtils.isEmpty(skText)) {
                Toast.makeText(this, "ip or AK or SK is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            HikConfig.host = ipText;
            HikConfig.appKey = akText;
            HikConfig.appSecret = skText;
            startActivity(new Intent(this, DeviceListActivity.class));
        });
    }
}