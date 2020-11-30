package com.example.myapplication.haikang.http;

import android.util.Log;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class My509TrustManager implements X509TrustManager {
    private static final String TAG = "My509TrustManager";
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d(TAG, "checkClientTrusted");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d(TAG, "TrustAllCerts checkServerTrusted");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
