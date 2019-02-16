package <%=packageName%>.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.core.util.config.InMemoryConfigManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class RealtimeAnalytics extends StreamCallback implements
   RealtimeAnalyticsService,ApplicationListener<ContextRefreshedEvent> {

    private  static final Logger log = LoggerFactory.getLogger(RealtimeAnalytics.class);
    private SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;
    private InputHandler stockInputHandler;
    String script = "define stream StockEventStream (symbol string, price float, volume long); " +
            " " +
            "@info(name = 'query1') " +
            "from StockEventStream#window.time(5 sec)  " +
            "select symbol, sum(price) as price, sum(volume) as volume " +
            "group by symbol " +
            "insert into AggregateStockStream ;";



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        log.info("Realtime analytics engine starting up");
        siddhiManager = new SiddhiManager();
        Map<String,String> stockConfig = new HashMap<>();

        InMemoryConfigManager inMemoryConfigManager = new InMemoryConfigManager(stockConfig,Collections.emptyMap());
        siddhiManager.setConfigManager(inMemoryConfigManager);
       // siddhiManager.setExtension();
        siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(script);

        siddhiAppRuntime.addCallback("AggregateStockStream",this);

        stockInputHandler = siddhiAppRuntime.getInputHandler("StockEventStream");

        siddhiAppRuntime.start();

            }



    @PreDestroy
    private void onApplicationExit(){

        log.info("Shutting down siddhi");
        siddhiAppRuntime.shutdown();
        siddhiManager.shutdown();
    }

    @Override
    public void boundDataEvent(String stockId, StockData data) {
        try {
            stockInputHandler.send(new Object[]{data.getSymbol(),data.getPrice(),data.getVolume()});
        } catch (InterruptedException e) {
            log.error("error occurred while feeding real time configurations", e);
        }
    }


    class EventprintingCallback extends StreamCallback{

        String msg;

        EventprintingCallback(String massege){
            this.msg=massege;

        }
        @Override
        public void receive(Event[] events) {
            if (log.isDebugEnabled()){
                log.debug("--------------------",msg);
                EventPrinter.print(events);
            }

        }
    }



    @Override
    public void receive(Event[] events) {


        EventPrinter.print(events);

    }






}
