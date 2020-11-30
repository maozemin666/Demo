package com.example.myapplication.haikang.http;

import android.util.Log;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class TrustAllHostnameVerifier implements HostnameVerifier {
    private static final String TAG = "TrustAllHostnameVerifier";

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{
                    new My509TrustManager()
            }, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            Log.d(TAG, "createSSLSocketFactory: exception");
        }

        return ssfFactory;
    }

    @Override
    public boolean verify(String requestedHost, SSLSession remoteServerSession) {
        return true;
    }
}
