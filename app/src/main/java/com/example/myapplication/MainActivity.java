package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.haikang.ArtemisPostTest;
import com.example.myapplication.haikang.bean.DeviceListResponse;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_device;
    private TextView tv_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_device = findViewById(R.id.button_device);
        tv_device = findViewById(R.id.tv_device);

        button_device.setOnClickListener(v -> startActivity(new Intent(this, DeviceListActivity.class)));
    }
}