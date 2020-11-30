package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.haikang.DeviceListAdapter;
import com.example.myapplication.haikang.LogUtil.SuperLog;
import com.example.myapplication.haikang.bean.DeviceListResponse;
import com.example.myapplication.haikang.bean.DeviceSearchRequest;
import com.example.myapplication.haikang.http.HikApi;
import com.example.myapplication.haikang.http.HikApiService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceListActivity extends AppCompatActivity {
    private static final String TAG = "DeviceListActivity";
    private RecyclerView rv_device;
    private DeviceListAdapter deviceListAdapter;
    private TextView tv_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        rv_device = findViewById(R.id.rv_device);
        tv_response = findViewById(R.id.response);

        rv_device.setLayoutManager(new LinearLayoutManager(this));
        deviceListAdapter = new DeviceListAdapter(this);
        rv_device.setAdapter(deviceListAdapter);
        initData();
    }

    private void initData() {
        requestDeviceList();
    }

    private void requestDeviceList() {
        DeviceSearchRequest request = new DeviceSearchRequest();
        request.setPageNo(1);
        request.setPageSize(3);
        Map<String, String> searchHeaderMap = HikApiService.getSearchHeaderMap(new Gson().toJson(request));

        HikApi.api().encodeDeviceSearch(searchHeaderMap, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == null) {
                        setResponseText("请求设备列表为空："+response.toString());
                        SuperLog.info2SD(TAG, "请求设备列表为空："+response.toString());
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        DeviceListResponse.DataDTO data = response.getData();
                        if (data == null || data.getList() == null || data.getList().size() <= 0) {
                            setResponseText("请求成功! 请求设备列表为空：" + response.toString());
                            SuperLog.info2SD(TAG, "请求成功! 请求设备列表为空：" + response.toString());
                            return;
                        }
                        setResponseText("请求成功：" + response.toString());
                        SuperLog.info2SD(TAG, "请求成功：" + response.toString());
                        List<DeviceListResponse.DataDTO.ListDTO> list = data.getList();
                        deviceListAdapter.setData(list);
                    } else {
                        SuperLog.info2SD(TAG, "请求设备列表fail：" + response.toString());
                        setResponseText("请求设备列表fail：" + response.toString());
                    }
                }, error -> {
                    SuperLog.info2SD(TAG, "请求设备列表异常：" + error.getMessage());
                    setResponseText("请求设备列表异常：" + error.getMessage());
                });
    }

    public void setResponseText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        tv_response.setText(text);
    }
}