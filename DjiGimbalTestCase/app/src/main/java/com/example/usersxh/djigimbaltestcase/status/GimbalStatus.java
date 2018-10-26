package com.example.usersxh.djigimbaltestcase.status;

public class GimbalStatus {
    double pith;
    double yaw;
    double roll;

    public GimbalStatus(double pith, double yaw, double roll) {
        this.pith = pith;
        this.yaw = yaw;
        this.roll = roll;
    }

    public double getPith() {
        return pith;
    }

    public void setPith(double pith) {
        this.pith = pith;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }
}
