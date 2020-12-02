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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private static final String TAG = "DeviceListAdapter";

    private final DeviceListActivity deviceListActivity;
    private List<DeviceListResponse.DataDTO.ListDTO> list;
    private Context context;

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
        holder.preview.setOnClickListener(v -> preview(listDTO));
        holder.controller.setOnClickListener(v -> controller(listDTO));
    }

    private void preview(DeviceListResponse.DataDTO.ListDTO listDTO) {
        String cameraIndexCode = listDTO.getCameraIndexCode();
        if (TextUtils.isEmpty(cameraIndexCode)) {
            Toast.makeText(context, "cameraIndexCode is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        PreviewRequest request = new PreviewRequest();
        request.setCameraIndexCode(cameraIndexCode);
        Map<String, String> headerMap = HikApiService.getPreviewHeaderMap(new Gson().toJson(request));

        deviceListActivity.addDisposable(HikApi.api().previewURLs(headerMap, request) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == null) {
                        deviceListActivity.setResponseText("预览结果为空："+response.toString());
                        SuperLog.info2SD(TAG, "预览结果为空："+response.toString());
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        PreviewResp.DataDTO data = response.getData();
                        if (data == null) {
                            SuperLog.info2SD(TAG, "预览请求成功! 预览fail：" + response.toString());
                            deviceListActivity.setResponseText("预览请求成功! 预览fail：" + response.toString());
                            return;
                        }
                        deviceListActivity.setResponseText("预览请求成功：" + response.toString());
                        SuperLog.info2SD(TAG, "预览请求成功：" + response.toString());
                    } else {
                        deviceListActivity.setResponseText("预览fail：" + response.toString());
                        SuperLog.info2SD(TAG, "预览fail：" + response.toString());
                    }
                }, error -> {
                    deviceListActivity.setResponseText("预览异常：" + error.getMessage());
                    SuperLog.info2SD(TAG, "预览异常：" + error.getMessage());
                }));
    }

    private void controller(DeviceListResponse.DataDTO.ListDTO listDTO) {
        String cameraIndexCode = listDTO.getCameraIndexCode();
        if (TextUtils.isEmpty(cameraIndexCode)) {
            Toast.makeText(context, "cameraIndexCode is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ControlRequest request = new ControlRequest();
        request.setCameraIndexCode(cameraIndexCode);
        request.setAction(1);
        request.setCommand("GOTO_PRESET");
        Map<String, String> headerMap = HikApiService.getControllerHeaderMap(new Gson().toJson(request));

        deviceListActivity.addDisposable(HikApi.api().controlling(headerMap, request) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == null) {
                        deviceListActivity.setResponseText("云台操作为空："+response.toString());
                        SuperLog.info2SD(TAG, "云台操作为空："+response.toString());
                        return;
                    }

                    if (TextUtils.equals(response.getCode(), "0")) {
                        deviceListActivity.setResponseText("云台操 作请求成功：" + response.toString());
                        SuperLog.info2SD(TAG, "云台操 作请求成功：" + response.toString());
                    } else {
                        deviceListActivity.setResponseText("云台操作fail：" + response.toString());
                        SuperLog.info2SD(TAG, "云台操作fail：" + response.toString());
                    }
                }, error -> {
                    deviceListActivity.setResponseText("云台操作异常：" + error.getMessage());
                    SuperLog.info2SD(TAG, "云台操作异常：" + error.getMessage());
                }));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
