package <%=packageName%>.domain;

public interface RealtimeAnalyticsService {

    void inboundDataEvent (String temperatureId , TemperatureData data);
}