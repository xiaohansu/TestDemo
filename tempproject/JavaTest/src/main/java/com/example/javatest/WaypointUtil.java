package com.example.javatest;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/10/4.
 */

public class WaypointUtil {
    public static void main(String[] main) {
        new Gson();

//        WaypointUtil demo = new WaypointUtil();
//        List<WayPoint> testdata = demo.createTestData();
//        testdata = WaypointUtil.getStayWaypoint(testdata, 0.5f);
//        for (WayPoint wp:testdata){
//            System.out.println(wp.alt);
//        }
    }


    static List<WayPoint> getStayWaypoint(List<WayPoint> datalist, float distance) {
        List<WayPoint> staypoints = new ArrayList<WayPoint>();
        List<WayPoint> currentstaypoint = new ArrayList<WayPoint>();
        for (int i = 1; i < datalist.size(); i++) {
            if (WayPoint.get3DDistance(datalist.get(i), datalist.get(i - 1)) < distance) {
                staypoints.add(datalist.get(i));
//                continue;
            } else {
                if (staypoints.size() != 0) {
                    double averalt = staypoints.stream().mapToDouble(WayPoint::getAlt).average().getAsDouble();
                    double averlat = staypoints.stream().mapToDouble(WayPoint::getLat).average().getAsDouble();
                    double averlot = staypoints.stream().mapToDouble(WayPoint::getLon).average().getAsDouble();
                    currentstaypoint.add(new WayPoint(averlat, averlot, averalt, 0));
//                    currentstaypoint.add(staypoints.get(0));
                    staypoints.clear();
                }
            }

        }
        System.out.println(currentstaypoint);
        return currentstaypoint;
    }




    List<WayPoint> createTestData() {
        List<WayPoint> testDatas = new ArrayList<WayPoint>();
        for (int i = 0; i < 100; i++) {
            testDatas.add(new WayPoint(i, i, i, 1));
            if (i % 9 == 0) {
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));
                testDatas.add(new WayPoint(i, i, i, 0));

            }
        }
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
        testDatas.add(new WayPoint(4, 4, 7.8, 0));
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