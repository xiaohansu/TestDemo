package com.example.javatest;

import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.*;

/**
 * Created by suxiaohan on 2018/9/17.
 */

public class SerializableDemo implements Serializable {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static void main(String[] argv){
        SerializableDemo box = new SerializableDemo();
        box.setHeight(50);
        box.setWidth(20);
        saveObject(box);
        SerializableDemo ret = readObject();
        System.out.println(ret.getHeight());

        writeFile("hello i am tired,i am not a fool!");

    }
    static void saveObject(SerializableDemo object){
        try {
            FileOutputStream fs = new FileOutputStream("foo.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(object);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    static SerializableDemo readObject(){
        SerializableDemo ret = null;
        try{
            FileInputStream fs = new FileInputStream("foo.ser");
            ObjectInput os = new ObjectInputStream(fs);
            ret = (SerializableDemo)os.readObject();
        }catch (Exception e){

        }
        return ret;

    }
    static void writeFile(String txt){
        try {
            FileWriter fileWriter = new FileWriter("foo.txt");
            fileWriter.write(txt);
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
