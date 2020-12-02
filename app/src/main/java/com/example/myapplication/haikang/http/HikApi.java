package com.example.myapplication.haikang.http;

import com.example.myapplication.haikang.HikConfig;
import com.example.myapplication.haikang.bean.ControlRequest;
import com.example.myapplication.haikang.bean.ControlResp;
import com.example.myapplication.haikang.bean.DeviceListResponse;
import com.example.myapplication.haikang.bean.DeviceSearchRequest;
import com.example.myapplication.haikang.bean.PreviewRequest;
import com.example.myapplication.haikang.bean.PreviewResp;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public class HikApi {
    public static final String BASE_URL = HikConfig.host;

    public static final String AK = HikConfig.appKey;

    public static final String SK = HikConfig.appSecret;

    private static final String ARTEMIS_PATH = "/artemis";

    public static final String DEVICE_SEARCH_PATH = ARTEMIS_PATH + "/api/resource/v2/encodeDevice/search";
    public static final String PREVIEW_PATH = ARTEMIS_PATH + "/api/video/v2/cameras/previewURLs";
    public static final String CONTROLLING_PATH = ARTEMIS_PATH + "/api/video/v1/ptzs/controlling";

    public static ApiService api() {
        return HikApiService.getInstance().getApi(BASE_URL);
    }

    public interface ApiService {
        @POST(DEVICE_SEARCH_PATH)
        Observable<DeviceListResponse> encodeDeviceSearch(@HeaderMap Map<String,String> map, @Body DeviceSearchRequest request);

        @POST(PREVIEW_PATH)
        Observable<PreviewResp> previewURLs(@HeaderMap Map<String,String> map, @Body PreviewRequest request);

        @POST(CONTROLLING_PATH)
        Observable<ControlResp> controlling(@HeaderMap Map<String,String> map, @Body ControlRequest request);
    }
}
