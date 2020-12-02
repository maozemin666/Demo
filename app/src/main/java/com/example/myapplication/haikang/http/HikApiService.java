package com.example.myapplication.haikang.http;

import com.hikvision.artemis.sdk.constant.ContentType;
import com.hikvision.artemis.sdk.constant.HttpHeader;
import com.hikvision.artemis.sdk.constant.HttpMethod;
import com.hikvision.artemis.sdk.util.SignUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HikApiService {
    public static final String TAG = "HttpManager";

    private HikApiService() {
    }

    public HikApi.ApiService getApi(String baseUrl) {
        return getRetrofit(baseUrl).create(HikApi.ApiService.class);
    }

    private static class SingleHolder {
        private static final HikApiService INSTANCE = new HikApiService();
    }

    public static HikApiService getInstance() {
        return SingleHolder.INSTANCE;
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .sslSocketFactory(TrustAllHostnameVerifier.createSSLSocketFactory(), new My509TrustManager())
                .build();
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Map<String, String> getSearchHeaderMap(String body) {
        return generateBasicHeader(HikApi.DEVICE_SEARCH_PATH, body, null, null, "application/json;charset=UTF-8"
                , null, null);
    }

    public static Map<String, String> getPreviewHeaderMap(String body) {
        return generateBasicHeader(HikApi.PREVIEW_PATH, body, null, null, "application/json;charset=UTF-8"
                , null, null);
    }

    public static Map<String, String> getControllerHeaderMap(String body) {
        return generateBasicHeader(HikApi.CONTROLLING_PATH, body, null, null, "application/json;charset=UTF-8"
                , null, null);
    }

    public static Map<String, String> generateBasicHeader(String path, String body, Map<String, String> querys,
                                                          String accept, String contentType, Map<String, String> header, List<String> signHeaderPrefixList) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "*/*");
        if (StringUtils.isNotBlank(accept)) {
            headers.put(HttpHeader.HTTP_HEADER_ACCEPT, accept);
        } else {
            headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "*/*");
        }
        if (StringUtils.isNotBlank(contentType)) {
            headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, contentType);
        } else {
            headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);
        }
        if (header != null) {
            headers.putAll(header);
        }
        if (ContentType.CONTENT_TYPE_FORM.equals(contentType)) {//postString发鿁content-type为表单的请求，请求的body字符串必须为key-value组成的串，类似a=1&b=2这种形式
            Map<String, String> paramMap = strToMap(body);

            String modelDatas = paramMap.get("modelDatas"); //这个base64的字符串经过url编码，签名时先解砿(这个是针对大数据某个请求包含url编码的参敿)，对某个请求包含的参数的特殊处理
            if (StringUtils.isNotBlank(modelDatas)) {
                paramMap.put("modelDatas", URLDecoder.decode(modelDatas));
            }

            headers = initialBasicHeader(HttpMethod.POST, path, headers, querys, paramMap, signHeaderPrefixList, HikApi.AK, HikApi.SK);
        } else {
            headers = initialBasicHeader(HttpMethod.POST, path, headers, querys, null, signHeaderPrefixList, HikApi.AK, HikApi.SK);
        }
        return headers;
    }

    private static Map<String, String> initialBasicHeader(String method, String path, Map<String, String> headers, Map<String, String> querys,
                                                          Map<String, String> bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) {
        if (headers == null) {
            headers = new HashMap<>();
        }

        headers.put("x-ca-timestamp", String.valueOf((new Date()).getTime()));
        headers.put("x-ca-nonce", UUID.randomUUID().toString());
        headers.put("x-ca-key", appKey);
        headers.put("x-ca-signature", SignUtil.sign(appSecret, method, path, (Map) headers, querys, bodys, signHeaderPrefixList));
        return headers;
    }

    private static Map<String, String> strToMap(String str) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String[] params = str.split("&");
            for (String param : params) {
                String[] a = param.split("=");
                map.put(a[0], a[1]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
