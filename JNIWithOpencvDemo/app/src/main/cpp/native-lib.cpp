#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/opencv.hpp>
#define TAG "myDemo-jni"
#include<android/log.h>
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

using namespace std;
using namespace cv;

char filepath1[100] = "/storage/emulated/0/Download/PacktBook/Chapter6/panorama_stitched.jpg";
extern "C"
{
//int toGray(Mat img,Mat &gray);
//JNIEXPORT jint JNICALL Java_com_example_suxiaohan_jniwithopencvdemo_MainActivity_grayProc(JNIEnv *env, jobject instance,
//                                                                                          jlong addrRgba, jlong addrGray) {
//    Mat & mRgb = *(Mat *)addrRgba;
//    Mat & mGray = * (Mat *)addrGray;
//    int conv;
//    jint retVal;
//    conv = toGray(mRgb,mGray);
//    retVal = (jint)conv;
//    return retVal;
//}
//
//JNIEXPORT jint JNICALL Java_com_example_suxiaohan_jniwithopencvdemo_MainActivity_stitchpan(JNIEnv *env, jobject instance,
//                                                   jlong addrPic1, jlong addrPic2,jlong addrRes){
//    jint result = 0;
//    Mat & msrc1 = *(Mat *)addrPic1;
//    Mat & msrc2 = * (Mat *)addrPic2;
//    Mat & resimg = *(Mat *)addrRes;
//
//    vector<Mat> Images = vector<Mat>();
//    cvtColor(msrc1,msrc1,CV_RGB2BGR);
//    cvtColor(msrc2,msrc2,CV_RGB2BGR);
//    Images.push_back(msrc1);
//    Images.push_back(msrc2);
//
//    Stitcher stitcher = Stitcher::createDefault();
//    Stitcher::Status status = stitcher.stitch(Images, resimg);
//    cvtColor(resimg,resimg,CV_BGR2RGB);
//    //imwrite(filepath1, resimg);
//    if (status == Stitcher::OK)
//        result = 1;
//    else
//        result = 0;
//
//    return result;
//}


JNIEXPORT jint JNICALL Java_com_example_suxiaohan_jniwithopencvdemo_MainActivity_StitchPanorama(JNIEnv* env, jobject, jobjectArray images, jint size, jlong resultMatAddr)
{
    jint result = 0;
    vector<Mat> clickedImages = vector<Mat>();
    Mat& srcRes = *(Mat*)resultMatAddr, img;
    Mat output_stitched = Mat();

    jclass clazz = (env)->FindClass("org/opencv/core/Mat");
    jmethodID getNativeObjAddr = (env)->GetMethodID(clazz, "getNativeObjAddr", "()J");

    for(int i=0; i < size; i++){
        jobject obj = (env->GetObjectArrayElement(images, i));
        jlong result = (env)->CallLongMethod(obj, getNativeObjAddr, NULL);
        img = *(Mat*)result;
        //resize(img, img, Size(img.rows/10, img.cols/10));
        cvtColor(img,img,CV_RGB2BGR);//
        clickedImages.push_back(img);
        env->DeleteLocalRef(obj);
    }
    LOGW("ceshi%d",(int)clickedImages.size());
    env->DeleteLocalRef(images);


    Stitcher stitcher = Stitcher::createDefault();
    Stitcher::Status status = stitcher.stitch(clickedImages, srcRes);

    if (status == Stitcher::OK){
        cvtColor(srcRes,srcRes,CV_BGR2RGB);
        result = 1;}

    else
        result = 0;


    return result;
}
//int toGray(Mat img,Mat& gray){
//    cvtColor(img,gray,CV_RGBA2GRAY);
//    if(gray.rows==img.rows && gray.cols == img.cols){
//        return 1;
//    }
//    return 0;
//}


}