package com.example.myapplication.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    private static String secret = "yOmXQCNQQlV479JMdlVR";

    public static String getSigned() {
        Mac hmacSha256 = null;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = secret.getBytes("UTF-8");   //secret 为 APP 的密钥。
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            String sign = new String(Base64.encodeBase64(hmacSha256.doFinal(getSign().getBytes("UTF-8"))));
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSign() {
        long time = getTime();
        return "POST\\n\n" +
                "*/*\\n\n" +
                "text/plain;charset=UTF-8\\n\n" +
                "x-ca-key:21780458\\n\n" +
                "x-ca-timestamp:" + time + "\\n\n" +
                "/artemis/api/v1/oauth/token";
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        System.out.println("getSigned = " + getSigned());
    }

}
