package com.example.javatest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by suxiaohan on 2018/9/24.
 */

public class InputSteamDemo {
    static String path = "/Users/suxiaohan/Desktop/data/test.txt";

    public static void main(String[] argv) {
        inputstream();
        inputStreamReader();
        bufferReader();
    }


    static void inputstream() {
        try {
            FileInputStream fis = new FileInputStream(path);
            int i = 0;
            while ((i = fis.read()) != -1) {
                System.out.println((char) i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void inputStreamReader() {
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(fis, "utf8");
            int i = 0;
            while ((i = reader.read()) != -1) {
                System.out.println(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void bufferReader() {
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(fis, "utf8");

            BufferedReader bufferedReader = new BufferedReader(reader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                System.out.println(content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
