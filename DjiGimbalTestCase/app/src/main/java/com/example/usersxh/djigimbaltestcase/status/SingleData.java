package com.example.usersxh.djigimbaltestcase.status;

public class SingleData {
    private GimbalStatus realData;
    private GimbalStatus testData;
    private String photoBase64;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    private String photoName;

    public GimbalStatus getRealData() {
        return realData;
    }

    public void setRealData(GimbalStatus realData) {
        this.realData = realData;
    }

    public GimbalStatus getTestData() {
        return testData;
    }

    public void setTestData(GimbalStatus testData) {
        this.testData = testData;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }


    public SingleData(GimbalStatus realData, GimbalStatus testData, String photoBase64,String photoName) {
        this.realData = realData;
        this.testData = testData;
        this.photoBase64 = photoBase64;
        this.photoName = photoName;
    }

    public SingleData() {
    }
}
