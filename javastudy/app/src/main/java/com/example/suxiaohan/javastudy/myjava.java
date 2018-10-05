package com.example.suxiaohan.javastudy;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/10/4.
 */

public class myjava {
    public static void main(String[] main) {

        List<WayPoint> testdata = new ArrayList<WayPoint>();
        testdata = myjava.getStayWaypoint(myjava.createTestData(), 1.5f);
        System.out.println(new Gson().toJson(testdata));
        List<WayPoint> wplist = new ArrayList<WayPoint>();
        wplist.add(new WayPoint(9,9,9,0));
        wplist.add(new WayPoint(1,1,1,1));
        System.out.println(checkIsAllwaypointDone(wplist,testdata));

    }


    static List<WayPoint> getStayWaypoint(List<WayPoint> datalist, float distance) {
        List<WayPoint> staypoints = new ArrayList<WayPoint>();
        List<WayPoint> currentstaypoint = new ArrayList<WayPoint>();
        for (int i = 0; i < datalist.size(); i++) {
            if (i>0 && WayPoint.get3DDistance(datalist.get(i), datalist.get(i - 1)) < distance) {
                staypoints.add(datalist.get(i));
            } else {
                if (staypoints.size() != 0) {
                    double averalt = staypoints.stream().mapToDouble(WayPoint::getAlt).average().getAsDouble();
                    double averlat = staypoints.stream().mapToDouble(WayPoint::getLat).average().getAsDouble();
                    double averlot = staypoints.stream().mapToDouble(WayPoint::getLon).average().getAsDouble();
                    currentstaypoint.add(new WayPoint(averlat, averlot, averalt, 0));
                    staypoints.clear();
                }
            }

        }
        if (staypoints.size() != 0) {
            double averalt = staypoints.stream().mapToDouble(WayPoint::getAlt).average().getAsDouble();
            double averlat = staypoints.stream().mapToDouble(WayPoint::getLat).average().getAsDouble();
            double averlot = staypoints.stream().mapToDouble(WayPoint::getLon).average().getAsDouble();
            currentstaypoint.add(new WayPoint(averlat, averlot, averalt, 0));
            staypoints.clear();
        }

        return currentstaypoint;
    }

    static boolean checkIsAllwaypointDone(List<WayPoint> missionPoints, List<WayPoint> dataPoints){
        boolean isAllPointDone = false;
        if (missionPoints.size()>dataPoints.size()){
            return isAllPointDone;
        }
        int finshCount = 0;
        for (WayPoint mswp:missionPoints){
            for (WayPoint datawp:dataPoints){
                if (WayPoint.get3DDistance(mswp,datawp)<0.1){
                    finshCount++;

                }
            }
        }
        if (finshCount>=missionPoints.size()){
            isAllPointDone = true;
        }
        else {
            isAllPointDone = false;
        }

        return  isAllPointDone;

    }



    static  List<WayPoint> createTestData() {
        List<WayPoint> testDatas = new ArrayList<WayPoint>();

        for (int i = 0; i < 100; i++) {
            testDatas.add(new WayPoint(i, i, i, 1));
            if (i % 9 == 0) {
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));

            }
        }
        testDatas.add(new WayPoint(4.0, 4.0, 7.8, 0));
        testDatas.add(new WayPoint(4.0, 4.0, 7.8, 0));
        testDatas.add(new WayPoint(4.0, 4.0, 7.8, 0));
        testDatas.add(new WayPoint(4.0, 4.0, 7.8, 0));
        testDatas.add(new WayPoint(4.0, 4.0, 7.8, 0));

        return testDatas;
    }

}

class WayPoint {
    float speed;
    double lat;
    double lon;
    double alt;

    public WayPoint(double lat, double lon, double alt, float speed) {
        this.speed = speed;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public WayPoint(double lat, double lon, double alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public WayPoint() {

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    static double get3DDistance(WayPoint p1, WayPoint p2) {
        return Math.sqrt(Math.pow(p1.alt - p2.alt, 2) + Math.pow(p1.lat - p2.lat, 2) + Math.pow(p1.lon - p2.lon, 2));
    }
}
