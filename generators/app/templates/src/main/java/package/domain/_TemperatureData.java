package <%=packageName%>.domain;

import java.util.Arrays;

public class TemperatureData {
    private int roomNo;
    private double temperature;
    private Long deviceId;


    public int getRoomNo() {
        return  roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public double getTemperature (){
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceID(Long deviceId) {
        this.deviceId = deviceId;
    }


    @Override
    public String toString(){
        return "TemperatureData{" +
                "roomNo='" + roomNo + '\'' +
                ", temperature=" +temperature +
                ", deviceId='" + deviceId+ '\'' +
                '}';
    }


}
