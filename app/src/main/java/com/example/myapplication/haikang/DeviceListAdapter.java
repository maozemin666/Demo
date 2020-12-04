package com.example.myapplication.haikang;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.haikang.bean.ControlRequest;
import com.example.myapplication.haikang.bean.DeviceListResponse;
import com.example.myapplication.haikang.bean.PreviewRequest;
import com.example.myapplication.haikang.bean.PreviewResp;
import com.example.myapplication.haikang.http.HikApi;
import com.example.myapplication.haikang.http.HikApiService;
import com.example.myapplication.haikang.log.SuperLog;
import com.example.myapplication.ui.DeviceListActivity;
import com.example.myapplication.ui.PreviewActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private static final String TAG = "DeviceListAdapter";

    private final DeviceListActivity deviceListActivity;
    private List<DeviceListResponse.DataDTO.ListDTO> list;
    private Context context;
    private final ArrayList<String> previewUrls = new ArrayList<>();

    public DeviceListAdapter(DeviceListActivity deviceListActivity) {
        this.deviceListActivity = deviceListActivity;
    }

    public void setData(List<DeviceListResponse.DataDTO.ListDTO> list) {
        this.list = list == null ? new ArrayList<>() : list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.device_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        DeviceListResponse.DataDTO.ListDTO listDTO = list.get(position);
        holder.itemTextView.setText(listDTO.getCameraName());
        holder.preview.setOnClickListener(v -> previewOne(listDTO));
        holder.controller.setOnClickListener(v -> controller(listDTO));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private void preview(DeviceListResponse.DataDTO.ListDTO listDTO, CountDownLatch latch) {
        String cameraIndexCode = listDTO.getCameraIndexCode();
        if (TextUtils.isEmpty(cameraIndexCode)) {
            Toast.makeText(context, "cameraIndexCode is empty", Toast.LENGTH_SHORT).show();
            latch.countDown();
            return;
        }

        PreviewRequest request = new PreviewRequest();
        request.setCameraIndexCode(cameraIndexCode);
        request.setStreamType(1);
        request.setProtocol(HikConfig.protocol);
        request.setExpand(HikConfig.expand);
        Map<String, String> headerMap = HikApiService.getPreviewHeaderMap(new Gson().toJson(request));

        deviceListActivity.addDisposable(HikApi.api().previewURLs(headerMap, request).subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    String jsonResp = deviceListActivity.gson.toJson(response);
                    if (response == null) {
                        setResponseText("预览结果为空：" + jsonResp);
                        SuperLog.info2SD(TAG, "预览结果为空：" + jsonResp);
                        latch.countDown();
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        PreviewResp.DataDTO data = response.getData();
                        if (data == null) {
                            SuperLog.info2SD(TAG, "预览请求成功! 预览fail：" + jsonResp);
                            setResponseText("预览请求成功! 预览fail：" + jsonResp);
                            latch.countDown();
                            return;
                        }
                        setResponseText("预览请求成功：" + jsonResp);
                        SuperLog.info2SD(TAG, "预览请求成功：" + jsonResp);
                        previewUrls.add(data.getUrl());
                    } else {
                        setResponseText("预览fail：" + jsonResp);
                        SuperLog.info2SD(TAG, "预览fail：" + jsonResp);
                    }
                    latch.countDown();
                }, error -> {
                    setResponseText("预览异常：" + error.getMessage());
                    SuperLog.info2SD(TAG, "预览异常：" + error.getMessage());
                    latch.countDown();
                }));
    }

    private void previewOne(DeviceListResponse.DataDTO.ListDTO listDTO) {
        previewUrls.clear();
        CountDownLatch latch = new CountDownLatch(1);

        preview(listDTO, latch);

        try {
            latch.await(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            SuperLog.info2SD(TAG, "preview CountDownLatch InterruptedException：");
        }

        SuperLog.info2SD(TAG, "previewOne previewUrls：" + previewUrls.size());
        if (previewUrls.size() <= 0) {
            setResponseText("当前预览url is empty");
            Toast.makeText(deviceListActivity, "预览当前资源失败，当前 url is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        PreviewActivity.startPreviewActivity(deviceListActivity, previewUrls);
    }

    public void previewAll() {
        if (getItemCount() <= 0) {
            Toast.makeText(deviceListActivity, "无资源预览", Toast.LENGTH_SHORT).show();
            return;
        }
        previewUrls.clear();
        CountDownLatch latch = new CountDownLatch(list.size());
        for (DeviceListResponse.DataDTO.ListDTO listDTO : list) {
            preview(listDTO, latch);
        }
        try {
            latch.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            SuperLog.info2SD(TAG, "preview CountDownLatch InterruptedException：");
        }

        SuperLog.info2SD(TAG, "previewAll previewUrls：" + previewUrls.size());
        if (previewUrls.size() <= 0) {
            setResponseText("当前预览url is empty");
            Toast.makeText(deviceListActivity, "预览全部失败，当前 url is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        PreviewActivity.startPreviewActivity(deviceListActivity, previewUrls);
    }

    private void controller(DeviceListResponse.DataDTO.ListDTO listDTO) {
        String cameraIndexCode = listDTO.getCameraIndexCode();
        if (TextUtils.isEmpty(cameraIndexCode)) {
            Toast.makeText(context, "cameraIndexCode is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ControlRequest request = new ControlRequest();
        request.setCameraIndexCode(cameraIndexCode);
        request.setAction(0);
        request.setCommand("LEFT");
        Map<String, String> headerMap = HikApiService.getControllerHeaderMap(new Gson().toJson(request));

        deviceListActivity.addDisposable(HikApi.api().controlling(headerMap, request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == null) {
                        setResponseText("云台操作为空：" + deviceListActivity.gson.toJson(response));
                        SuperLog.info2SD(TAG, "云台操作为空：" + deviceListActivity.gson.toJson(response));
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        setResponseText("云台操 作请求成功：" + deviceListActivity.gson.toJson(response));
                        SuperLog.info2SD(TAG, "云台操 作请求成功：" + deviceListActivity.gson.toJson(response));
                    } else {
                        setResponseText("云台操作fail：" + deviceListActivity.gson.toJson(response));
                        SuperLog.info2SD(TAG, "云台操作fail：" + deviceListActivity.gson.toJson(response));
                    }
                }, error -> {
                    setResponseText("云台操作异常：" + error.getMessage());
                    SuperLog.info2SD(TAG, "云台操作异常：" + error.getMessage());
                }));
    }

    private void setResponseText(String text) {
        deviceListActivity.setResponseText(text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button preview;
        Button controller;
        TextView itemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.device_name);
            preview = itemView.findViewById(R.id.device_prev);
            controller = itemView.findViewById(R.id.controller);
        }
    }
}
