package <%=packageName%>.web.rest;
import <%=packageName%>.domain.RealtimeAnalyticsserviceImpl;
import <%=packageName%>.domain.TemperatureData;
import <%=packageName%>.domain.RealtimeAnalyticsService:

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureDataResource {
    private final Logger log = LoggerFactory.getLogger(TemperatureDataResource.class);
    @Autowired
    RealtimeAnalyticsService realtimeAnalyticsService;

    @PostMapping("/temperature-data")

    public ResponseEntity<Void> addTemperatureData(@PathVariable String tempId, @RequestBody TemperatureData data){

        TemperatureData temperatureData = new  TemperatureData();
        temperatureData.setRoomNo(data.getRoomNo());
        temperatureData.setDeviceID(data.getDeviceID());
        temperatureData.setTemperature(data.getTemperature());
        realtimeAnalyticsService.inboundDataEvent(tempId,data);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
