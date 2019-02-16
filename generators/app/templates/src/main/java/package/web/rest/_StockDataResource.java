package <%=packageName%>.web.rest;
import <%=packageName%>.domain.RealtimeAnalytics;
import <%=packageName%>.domain.StockData;
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
public class StockDataResource {

    private final Logger log = LoggerFactory.getLogger(StockDataResource.class);

    @Autowired
    RealtimeAnalytics realtimeAnalytics;

    @PostMapping("/stock-data")

    public ResponseEntity <Void> AddStockData(@PathVariable String stockID, @RequestBody StockData data){

        StockData stockData = new StockData();
        stockData.setSymbol(data.getSymbol());
        stockData.setPrice(data.getPrice());
        stockData.setVolume(data.getVolume());
        realtimeAnalytics.boundDataEvent(stockID,data);
         return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
