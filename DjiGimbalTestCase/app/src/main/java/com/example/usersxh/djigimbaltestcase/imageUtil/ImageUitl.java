package com.example.usersxh.djigimbaltestcase.imageUtil;

import org.ow2.util.base64.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUitl {

    public static void main(String[] argv){
        System.out.println(image2Base64("C:\\Users\\user_sxh\\Desktop\\pic\\平台正摄\\DJI_0319.JPG"));
    }
    public static String image2Base64(String photoPath) {
        String imgStr = "";
        char[] chars = Base64.encode(GetPictureData(photoPath));
        imgStr = new String(chars);

        return imgStr;
    }

    public static boolean base2Image(String imgStr, String savePath, String fileName) {
        if (imgStr == null) {
            return false;
        }
        try {
            byte[] b = Base64.decode(imgStr.toCharArray());
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            File f = new File(savePath);
            if (!f.exists()) {
                f.mkdirs();//创建目录
            }
            //生成jpeg图片
//            String imgFilePath = "D:\\new4.jpg";//新生成的图片
            String imgFilePath = savePath + "\\\\" + fileName + ".jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] GetPictureData(String imagePath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
