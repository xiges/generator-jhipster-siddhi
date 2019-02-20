package <%=packageName%>.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.config.InMemoryConfigManager;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class RealtimeAnalyticsServiceImpl extends StreamCallback implements
        RealtimeAnalyticsService,ApplicationListener<ContextRefreshedEvent> {

    private  static final Logger log = LoggerFactory.getLogger(RealtimeAnalyticsServiceImpl.class);
    private SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;
    private InputHandler temperatureInputHandler;
    private static final String script = "define stream TempStream(roomNo int, temperature double, deviceId long); " +
            " " +
            "@info(name = 'avgTemperature') " +
            "from TempStream#window.time(60 sec)  " +
            "select avg(temperature) as temperature,deviceId " +
            "group by roomNo " +
            "insert into AvgTempStream ;";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        log.info("Realtime analytics engine starting up");
        siddhiManager = new SiddhiManager();
        Map<String,String> stockConfig = new HashMap<>();

        InMemoryConfigManager inMemoryConfigManager = new InMemoryConfigManager(stockConfig,Collections.emptyMap());
        siddhiManager.setConfigManager(inMemoryConfigManager);
        // siddhiManager.setExtension();
        siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(script);

        siddhiAppRuntime.addCallback("AvgTempStream",this);

        temperatureInputHandler = siddhiAppRuntime.getInputHandler("TempStream");

        siddhiAppRuntime.start();

    }



    @PreDestroy
    private void onApplicationExit(){

        log.info("Shutting down  WSO2 Siddhi");
        siddhiAppRuntime.shutdown();
        siddhiManager.shutdown();
    }

    @Override
    public void inboundDataEvent(String tempId,  TemperatureData data) {
        try {
            temperatureInputHandler.send(new Object[]{data.getRoomNo(),data.getTemperature(),data.getDeviceId()});
        } catch (InterruptedException e) {
            log.error("error occurred while feeding temperature data ", e);
        }
    }






    @Override
    public void receive(Event[] events) {



        // EventPrinter.print(events);
        log.debug("Avg Temperature",events);

    }






}
