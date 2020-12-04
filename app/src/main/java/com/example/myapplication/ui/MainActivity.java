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
import com.example.myapplication.haikang.http.HikApi;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String URL_SIZE = "urlSize";
    private SharedPreferences sp;
    private Button button_device;
    private Button save;
    private EditText ip;
    private EditText AK;
    private EditText SK;
    private EditText url_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_device = findViewById(R.id.button_device);
        save = findViewById(R.id.save);
        ip = findViewById(R.id.ip);
        AK = findViewById(R.id.AK);
        SK = findViewById(R.id.SK);
        url_size = findViewById(R.id.url_size);
        initData();
    }

    private void initData() {
        sp = getSharedPreferences("HiKang", Context.MODE_PRIVATE);
        initInfo();
        save.setOnClickListener(v -> saveInfo());

        button_device.setOnClickListener(v -> {
            String ipText = ip.getText().toString().trim();
            String akText = AK.getText().toString().trim();
            String skText = SK.getText().toString().trim();
            String urlSize = url_size.getText().toString().trim();
            if (TextUtils.isEmpty(ipText) || TextUtils.isEmpty(akText) || TextUtils.isEmpty(skText)) {
                Toast.makeText(this, "ip or AK or SK is empty", Toast.LENGTH_SHORT).show();
                resetConfig();
                startActivity(new Intent(this, DeviceListActivity.class));
                return;
            }
            if (!TextUtils.isDigitsOnly(urlSize)) {
                Toast.makeText(this, "输入请求视频资源个数的格式不正确！", Toast.LENGTH_SHORT).show();
                return;
            }

            HikApi.BASE_URL = ipText;
            HikApi.AK = akText;
            HikApi.SK = skText;
            HikConfig.urlSize = Integer.parseInt(urlSize);

            startActivity(new Intent(this, DeviceListActivity.class));
        });
    }

    private void resetConfig() {
        HikApi.BASE_URL = HikConfig.host;
        HikApi.AK = HikConfig.appKey;
        HikApi.SK = HikConfig.appSecret;
    }

    private void initInfo() {
        ip.setText(sp.getString("ipText", "https://183.215.16.254:446"));
        AK.setText(sp.getString("akText", "25701801"));
        SK.setText(sp.getString("skText", "m4rT982FymJ5zAgQmp5h"));
        url_size.setText(String.valueOf(sp.getInt(URL_SIZE, 9)));
    }

    private void saveInfo() {
        String ipText = ip.getText().toString().trim();
        String akText = AK.getText().toString().trim();
        String skText = SK.getText().toString().trim();
        String urlSize = url_size.getText().toString().trim();
        sp.edit().putString("ipText", ipText).apply();
        sp.edit().putString("akText", akText).apply();
        sp.edit().putString("skText", skText).apply();
        sp.edit().putString(URL_SIZE, urlSize).apply();
    }
}