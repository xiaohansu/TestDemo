package com.example.javatest;




import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/9/29.
 */

public class SortDemo {

    class NameCompare implements Comparator<mountain>{
        @Override
        public int compare(mountain mountain, mountain t1) {
            return mountain.getName().compareTo(t1.getName());

        }
    }

    class HeightCompare implements Comparator<mountain>{
        @Override
        public int compare(mountain mountain, mountain t1) {
            if (mountain.getHeight()>t1.getHeight()){
                return 1;
            }else if (mountain.getHeight() == t1.getHeight()){
                return 0;
            }else {
                return -1;
            }
        }
    }

    List<mountain> mtn = new LinkedList<mountain>();
    public static void main(String[] argv){
//        Gson g = new Gson();
        new SortDemo().go();
    }
    public void go(){
        mtn.add(new mountain("longs",455));
        mtn.add(new mountain("Tailand",45));
        mtn.add(new mountain("Mouse",49));
        mtn.add(new mountain("Hlit",675));

        Collections.sort(mtn,new HeightCompare());
        for (mountain m : mtn){
            System.out.println(m.getHeight());
        }
    }


}
class mountain{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    String name;
    int height;

    public mountain(String name, int height) {
        this.name = name;
        this.height = height;
    }
}