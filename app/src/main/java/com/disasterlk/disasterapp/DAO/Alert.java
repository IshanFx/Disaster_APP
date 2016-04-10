package com.disasterlk.disasterapp.DAO;

/**
 * Created by IshanFx on 4/7/2016.
 */
public class Alert {
    private String A_id;
    private String Area_name;
    private String ADisasterType;
    private String AMessage;
    private String ADate;
    private String ARiskLevel;
    private String Alocation;

    public String getA_id() {
        return A_id;
    }

    public void setA_id(String a_id) {
        A_id = a_id;
    }

    public String getArea_name() {
        return Area_name;
    }

    public void setArea_name(String area_name) {
        Area_name = area_name;
    }

    public String getAlocation() {
        return Alocation;
    }

    public void setAlocation(String alocation) {
        Alocation = alocation;
    }

    public String getADisasterType() {
        return ADisasterType;
    }

    public void setADisasterType(String ADisasterType) {
        this.ADisasterType = ADisasterType;
    }

    public String getAMessage() {
        return AMessage;
    }

    public void setAMessage(String AMessage) {
        this.AMessage = AMessage;
    }

    public String getADate() {
        return ADate;
    }

    public void setADate(String ADate) {
        this.ADate = ADate;
    }

    public String getARiskLevel() {
        return ARiskLevel;
    }

    public void setARiskLevel(String ARiskLevel) {
        this.ARiskLevel = ARiskLevel;
    }
}
