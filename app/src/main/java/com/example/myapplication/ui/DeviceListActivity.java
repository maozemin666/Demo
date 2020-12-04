package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.haikang.DeviceListAdapter;
import com.example.myapplication.haikang.HikConfig;
import com.example.myapplication.haikang.bean.DeviceListResponse;
import com.example.myapplication.haikang.bean.DeviceSearchRequest;
import com.example.myapplication.haikang.http.HikApi;
import com.example.myapplication.haikang.http.HikApiService;
import com.example.myapplication.haikang.log.SuperLog;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceListActivity extends AppCompatActivity {
    private static final String TAG = "DeviceListActivity";
    private RecyclerView rv_device;
    private DeviceListAdapter deviceListAdapter;
    private TextView tv_response;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public final Gson gson = new Gson();
    private RadioGroup fetch_stream;
    private RadioGroup out_stream;
    private Button preview_nine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        initView();
        initData();
    }

    private void initView() {
        rv_device = findViewById(R.id.rv_device);
        tv_response = findViewById(R.id.response);
        fetch_stream = findViewById(R.id.fetch_stream);
        out_stream = findViewById(R.id.out_stream);
        preview_nine = findViewById(R.id.preview_nine);

        fetch_stream.setOnCheckedChangeListener(getFetchStreamSelectListener());
        out_stream.setOnCheckedChangeListener(getOutStreamSelectListener());
        preview_nine.setOnClickListener(v -> deviceListAdapter.previewAll());

        rv_device.setLayoutManager(new LinearLayoutManager(this));
        deviceListAdapter = new DeviceListAdapter(this);
        rv_device.setAdapter(deviceListAdapter);
    }

    private void initData() {
        requestDeviceList();
    }

    private void requestDeviceList() {
        DeviceSearchRequest request = new DeviceSearchRequest();
        request.setPageNo(1);
        request.setPageSize(HikConfig.urlSize);
        Map<String, String> searchHeaderMap = HikApiService.getSearchHeaderMap(gson.toJson(request));

        compositeDisposable.add(HikApi.api().encodeDeviceSearch(searchHeaderMap, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    String jsonResponse = gson.toJson(response);
                    if (response == null) {
                        setResponseText("请求设备列表为空：" + jsonResponse);
                        SuperLog.info2SD(TAG, "请求设备列表为空：" + jsonResponse);
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        DeviceListResponse.DataDTO data = response.getData();
                        if (data == null || data.getList() == null || data.getList().size() <= 0) {
                            setResponseText("请求成功! 请求设备列表为空：" + jsonResponse);
                            SuperLog.info2SD(TAG, "请求成功! 请求设备列表为空：" + jsonResponse);
                            return;
                        }

                        setResponseText("请求成功：" + jsonResponse);
                        SuperLog.info2SD(TAG, "请求成功：" + jsonResponse);
                        List<DeviceListResponse.DataDTO.ListDTO> list = data.getList();
                        deviceListAdapter.setData(list);
                    } else {
                        SuperLog.info2SD(TAG, "请求设备列表fail：" + jsonResponse);
                        setResponseText("请求设备列表fail：" + jsonResponse);
                    }
                }, error -> {
                    SuperLog.info2SD(TAG, "请求设备列表异常：" + error.getMessage());
                    setResponseText("请求设备列表异常：" + error.getMessage());
                }));
    }

    private RadioGroup.OnCheckedChangeListener getFetchStreamSelectListener() {
        return (radioGroup, checkedId) -> {
            if (checkedId == R.id.rtsp) {
                HikConfig.protocol = "rtsp";
            } else if (checkedId == R.id.rtmp) {
                HikConfig.protocol = "rtmp";
            } else if (checkedId == R.id.hls) {
                HikConfig.protocol = "hls";
            } else {
                HikConfig.protocol = "rtsp";
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener getOutStreamSelectListener() {
        return (radioGroup, checkedId) -> {
            if (checkedId == R.id.ps) {
                HikConfig.expand = "streamform=ps";
            } else if (checkedId == R.id.rtp) {
                HikConfig.expand = "streamform=rtp";
            } else if (checkedId == R.id.gb28181) {
                HikConfig.expand = "streamform=gb28181";
            } else if (checkedId == R.id.ts) {
                HikConfig.expand = "streamform=ts";
            } else {
                HikConfig.expand = "streamform=ps";
            }
        };
    }

    public void setResponseText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        tv_response.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }
}