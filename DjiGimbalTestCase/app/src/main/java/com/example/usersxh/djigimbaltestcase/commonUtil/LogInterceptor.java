package com.example.usersxh.djigimbaltestcase.commonUtil;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LogInterceptor implements Interceptor {

    public String TAG = "LogInterceptor";
    private static int code = 0;


    //    private static long lastTime=0;
//    private int count=0;
//,"detail":"{\"requestTime\":1509262598287,\"elapseTime\":176,\"withError\":false}","type":"network"
    public LogInterceptor() {
        code++;
        TAG = "LogInterceptor-" + code;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
//        if(lastTime==0){
//            lastTime=System.currentTimeMillis();
//        }
        boolean isWithError = false;
        IOException execption = null;
        Request request = chain.request();
//        Log.d(TAG, "intercept: request url=" + request.url());
//        Log.d(TAG, "intercept: request headers=" + request.headers());
        Response response = null;
        long startTime = System.currentTimeMillis();
        try {
            response = chain.proceed(chain.request());
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e(TAG, "intercept: exception ", e.getCause());
//            mDylogger.TRACE.o("Response",e);
//            throw e;
            execption = e;
            isWithError = true;
        }
//        if(System.currentTimeMillis()-lastTime<1000){
//            count++;
//        }else{
//            mDylogger.INFO.o(DYLogger.createMap("NetWorkCheck",DYLogger.createMap("RequestTimes",count)));
//            count=0;
//            lastTime=System.currentTimeMillis();
//        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        if (request.url().toString().contains("api/info")) {
            Map<String, Object> map = new HashMap<>();
            map.put("requestTime", startTime);
            map.put("elapseTime", duration);
            map.put("withError", isWithError);
        }
        if (isWithError) {
            throw execption;
        }
        MediaType mediaType = response.body().contentType();
        String content = null;
        try {
            content = response.body().string();
//            Log.d(TAG, "intercept: content=" + content);
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e(TAG, "intercept: excption =" + e.getCause());
            throw e;
        }
//        Log.i(TAG,"\n");
//        Log.i(TAG,"----------Start----------------");
//        Log.i(TAG, "| Request "+request.url());
        String method = request.method();
//        if(!method.equalsIgnoreCase("post")) {
//            mDylogger.INFO.o(DYLogger.createMap("Requst", request.url()));
//        }
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + ":" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
//                Log.i(TAG, "| RequestParams:{" + sb.toString() + "}");
//                mDylogger.INFO.o("RequestParams:{"+sb.toString()+"}");
            }
        }
//        Log.i(TAG, "| Response:" + content);
//        if(!method.equalsIgnoreCase("post")) {
//            mDylogger.INFO.o(DYLogger.createMap(" Response:", content));
//        }
//        Log.i(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}


