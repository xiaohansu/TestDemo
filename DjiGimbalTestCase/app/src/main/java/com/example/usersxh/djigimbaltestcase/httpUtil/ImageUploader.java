package com.example.usersxh.djigimbaltestcase.httpUtil;

import com.example.usersxh.djigimbaltestcase.commonUtil.LogInterceptor;
import com.example.usersxh.djigimbaltestcase.imageUtil.ImageUitl;
import com.example.usersxh.djigimbaltestcase.status.GimbalStatus;
import com.example.usersxh.djigimbaltestcase.status.SingleData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 75732 on 2018/10/26
 */

public class ImageUploader {
    static Gson gson = new Gson();
    public static void main(String[] argv) {
        final SingleData testData = new SingleData();
        GimbalStatus targetStatus = new GimbalStatus(60,32,45);
        GimbalStatus realStatus = new GimbalStatus(22,22,66);

        testData.setPhotoBase64(ImageUitl.image2Base64("C:\\Users\\user_sxh\\Desktop\\pic\\平台正摄\\DJI_0319.JPG"));
        testData.setRealData(realStatus);
        testData.setTestData(targetStatus);
        testData.setPhotoName("");

        final ImageUploader imageUploader = new ImageUploader(new OkHttpClient.Builder().addNetworkInterceptor(new LogInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build(), "192.168.6.76", 9000, "test");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    imageUploader.postImages(testData, new UploadCallback() {
                        @Override
                        public void onSuccess() {
                            System.out.println("successful");
                        }

                        @Override
                        public void onFail(String error) {
                            System.out.println("fail");

                        }
                    });

                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        }).start();


    }


    private OkHttpClient okHttpClient;
    private String server;
    private int port;
    private String path;
    private StringBuilder url;
    private String url_base;
    private Gson _gson;
    private long seq = 0;
    private static final MediaType MIME_JSON = MediaType.parse("application/json; charset=utf-8");

    public ImageUploader(OkHttpClient okHttpClient, String server, int port, String path) {
        this.okHttpClient = okHttpClient;
        this.server = server;
        this.port = port;
        this.path = path;
        url = new StringBuilder();
        _gson = new Gson();
        url_base = "http://" + server + ":" + port + "/" + path;
        System.out.println(url_base);
    }


    public interface UploadCallback {
        void onSuccess();

        void onFail(String error);
    }

    public void postImages(Object object, UploadCallback uploadCallback) {
        RequestBody body = RequestBody.create(MIME_JSON, _gson.toJson(object));
        Request request = new Request.Builder().url(url_base).post(body).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                uploadCallback.onSuccess();
            }
            else {
                uploadCallback.onFail("up load fail ");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            uploadCallback.onFail(e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}

class temp {
    int age = 0;
    String name = "hh";
}