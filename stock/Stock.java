package stock_trading.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
@JsonSerialize
@JsonIgnoreProperties
public class Stock  {
    private String stockSymbol;
    private Category category;
    private Integer price;

    public Stock(){}
    public Stock(String stockSymbol,Category category,Integer price){
        this.stockSymbol = stockSymbol;
        this.category = category;
        this.price = price;
    }
    public Stock(Stock stock){
        this.price = stock.getPrice();
        this.category = stock.getCategory();
        this.stockSymbol = stock.getStockSymbol();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public Integer getPrice() {
        return price;
    }

    public Category getCategory(){
        return category;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-10s | %-5d", stockSymbol, category, price);
    }

    public void changePrice(int i) {
        this.price += i;
    }

    protected void setPrice(int price) {
        this.price = price;
    }
}
