package <%=packageName%>.domain;

import java.util.Arrays;

public class StockData {
    private String symbol;
    private Float price;
    private Long volume;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price1) {
        this.price = price;
    }


    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }


    @Override
    public String toString(){
        return "stockData{" +
                "symbol='" + symbol + '\'' +
                ", price1=" + price +
                ", volume='" + volume + '\'' +
                '}';
    }


}
