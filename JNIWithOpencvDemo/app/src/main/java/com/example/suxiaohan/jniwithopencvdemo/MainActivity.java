package com.example.suxiaohan.jniwithopencvdemo;
//http://johnhany.net/2017/07/opencv-ndk-dev-with-cmake-on-android-studio/注意使用的是opencvandroidsdk3。2
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.Utils;


import org.opencv.core.Mat;

import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import me.nereo.multi_image_selector.MultiImageSelector;



public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int maxNum = 15;
    private int result = 0;
    /**
     * 显示VR360度全景图片的控件
     */
    private VrPanoramaView vr_pan_view;//在该线程创立，只能在该线程操作
    /**
     * 打印的TAG
     */
    private final String TAG = "VrPanoramaView";

    private TextView tvshowPhotoNum;
    private Button btnProc;
    private Button btnSaveImage;
    private ImageButton ibtOpenCamera;
    private ImageButton ibtOpenFiles;
    private ImageView imageView;
    private DrawerLayout drawerLayout;
    ArrayList<Mat> clickedImages = new ArrayList<Mat>();
    private ArrayList<String> mSelectPath;
    private Mat res = new Mat();

    private ExecutorService mFixedThreadExecutor;

//    public static native int grayProc(long srcadr,long resadr);
//    public static native int stitchpan(long src1,long src2,long addres);
    public native int StitchPanorama(Object images[], int size, long addrSrcRes);
    static {
        System.loadLibrary("native-lib");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFixedThreadExecutor = Executors.newFixedThreadPool(3);
        initUI();

    }


    private void initUI(){
        btnProc = (Button) findViewById(R.id.btn_gray_process);
        btnSaveImage = (Button) findViewById(R.id.btn_save_image);
        ibtOpenCamera = (ImageButton)findViewById(R.id.ibt_open_camera);
        ibtOpenFiles = (ImageButton)findViewById(R.id.ibt_open_fileList);
        imageView = (ImageView) findViewById(R.id.image_view);
        tvshowPhotoNum = (TextView)findViewById(R.id.tv_photo_num);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout) ;
        tvshowPhotoNum.setVisibility(View.INVISIBLE);
        btnProc.setOnClickListener(this);
        ibtOpenCamera.setOnClickListener(this);
        imageView.setOnClickListener(this);
        ibtOpenFiles.setOnClickListener(this);
        btnSaveImage.setOnClickListener(this);

        vr_pan_view = (VrPanoramaView) findViewById(R.id.vr_pan_view);
        vr_pan_view.setInfoButtonEnabled(false);//隐藏信息按钮
       //vr_pan_view.setStereoModeButtonEnabled(false);//隐藏cardboard按钮
//        Bitmap bm = BitmapFactory.decodeResource(this.getResources(),R.drawable.andes);
//        VrPanoramaView.Options options = new VrPanoramaView.Options();
//        options.inputType = VrPanoramaView.Options.TYPE_MONO;
//        vr_pan_view.loadImageFromBitmap(bm, options);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_gray_process:
                if (result == 1){
                    Toast.makeText(MainActivity.this, "请重新选择照片", Toast.LENGTH_LONG).show();
                }else {
                    vr_pan_view.pauseRendering();//关闭渲染节省内存
                    btnProc.setClickable(false);
                    btnProc.setText("处理中。。。");
                    getPickImage();
                    mFixedThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (clickedImages.size() >= 2) {
                                result = StitchPanorama(clickedImages.toArray(), clickedImages.size(), res.getNativeObjAddr());
                                Log.i("debug", "" + result);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btnProc.setClickable(true);
                                        btnProc.setText("合成");
                                        if (result == 1) {
                                            Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
                                            Bitmap bitmap = Bitmap.createBitmap(res.cols(), res.rows(), Bitmap.Config.ARGB_8888);
                                            Utils.matToBitmap(res, bitmap);
                                            load360Image(bitmap);
                                            Log.i("debug", "" + result);
                                            vr_pan_view.resumeRendering();//开启渲染
                                        } else {
                                            vr_pan_view.resumeRendering();
                                            Toast.makeText(MainActivity.this, "失败，请重新选择照片" , Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "请选中至少两张图片", Toast.LENGTH_LONG).show();
                                        btnProc.setClickable(true);
                                        btnProc.setText("合成");
                                    }
                                });
                            }
                        }
                    });

                }

                break;

            case R.id.image_view:
                pickImage();
                result = 0;
                break;
            case R.id.ibt_open_camera:
                Intent intent = new Intent(); //调用照相机
                intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
                startActivity(intent);

                break;
            case R.id.ibt_open_fileList:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    drawerLayout.openDrawer(Gravity.LEFT);

                }

                break;
            case R.id.btn_save_image:
                saveImage("demo2.jpg");
                break;
            default:
                break;
        }

    }


    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                getString(R.string.mis_permission_rationale),
                REQUEST_STORAGE_READ_ACCESS_PERMISSION);
    }else {
        mSelectPath = new ArrayList<>();
        MultiImageSelector selector = MultiImageSelector.create(MainActivity.this);
        selector.showCamera(false);
        selector.count(maxNum);
        selector.multi();//or:single
        selector.origin(mSelectPath);
        selector.start(MainActivity.this, REQUEST_IMAGE);
    }
}
    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if(mSelectPath.size() == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvshowPhotoNum.setText(""+mSelectPath.size());
                            imageView.setImageResource(R.drawable.addphotos);
                            tvshowPhotoNum.setVisibility(View.INVISIBLE);
                        }
                    });

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvshowPhotoNum.setText(""+mSelectPath.size());
                            Bitmap bm = BitmapFactory.decodeFile(mSelectPath.get(0));
                            imageView.setImageBitmap(bm);
                            tvshowPhotoNum.setVisibility(View.VISIBLE);
                        }
                    });
                }


            }
        }
    }

    private void getPickImage(){
        if (clickedImages != null) {
            clickedImages.clear();
        }
        if (mSelectPath != null) {
            for (String p : mSelectPath) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(p,opts);
                Mat src = new Mat();
                Utils.bitmapToMat(bitmap, src);
                if(bitmap != null && !bitmap.isRecycled()){
                    bitmap.recycle();
                }
                System.gc();//调用垃圾收集器
                clickedImages.add(src);
            }
        }
    }

//////////////////////////////////////////////////////
    private void saveImage(String picName){
        if (res.rows()!=0&&res.cols()!=0){
            Bitmap bitmap = Bitmap.createBitmap(res.cols(), res.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(res, bitmap);
//            File file = new File("/mnt/sdcard/work/mywork");
////判断文件夹是否存在，如果不存在就创建，否则不创建
//            if (!file.exists()) {
//                //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
//                file.mkdirs();
//            }

            String filePath = "/mnt/sdcard/vrmaker";
            Log.i("debug",filePath);
            FileOutputStream outputStream = null;
            File file = new File(filePath);
            if(!file.exists() ){
                    file.mkdirs();
                }
            try {
                File pic = new File(filePath+"/"+picName);
                pic.createNewFile();
                outputStream = new FileOutputStream(pic);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                if(outputStream != null){
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
//////////////////////////////////////////////////////
    private void load360Image(Bitmap bitmap) {
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.andes);
        /**设置加载VR图片的相关设置**/
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;

        /**设置加载VR图片监听**/
        vr_pan_view.setEventListener(new VrPanoramaEventListener() {
            /**
             * 显示模式改变回调
             * 1.默认
             * 2.全屏模式
             * 3.VR观看模式，即横屏分屏模式
             * @param newDisplayMode 模式
             */
            @Override
            public void onDisplayModeChanged(int newDisplayMode) {
                super.onDisplayModeChanged(newDisplayMode);
                Log.d(TAG, "onDisplayModeChanged()->newDisplayMode=" + newDisplayMode);
            }

            /**
             * 加载VR图片失败回调
             * @param errorMessage
             */
            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Log.d(TAG, "onLoadError()->errorMessage=" + errorMessage);
            }

            /**
             * 加载VR图片成功回调
             */
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                Log.d(TAG, "onLoadSuccess->图片加载成功");
            }

            /**
             * 点击VR图片回调
             */
            @Override
            public void onClick() {
                super.onClick();
                Log.d(TAG, "onClick()");
            }
        });
        /**加载VR图片**/
        vr_pan_view.loadImageFromBitmap(bitmap, options);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**关闭加载VR图片，释放内存**/
        vr_pan_view.pauseRendering();
        vr_pan_view.shutdown();
    }
    @Override
    public void onResume(){
        super.onResume();
    }


}