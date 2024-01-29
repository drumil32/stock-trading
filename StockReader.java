package stock_trading;

import com.fasterxml.jackson.databind.ObjectMapper;
import stock_trading.exception.InvalidStockSymbol;
import stock_trading.stock.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockReader {
    static String folderPath = "./stock_info";
    public static Stock readTheStockBySymbol(String symbol) throws InvalidStockSymbol{
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = folderPath + "/" + symbol + ".json";
        try {
            File file = new File(filePath);
            Stock stock = objectMapper.readValue(file, Stock.class);
            return stock;
        } catch (FileNotFoundException e) {
            throw new InvalidStockSymbol();
        }catch (IOException e){
            return null;
        }
    }
    public static List<Stock> readStocksFromJsonFiles() {
        List<Stock> stocks = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    try {
                        Stock stock = objectMapper.readValue(file, Stock.class);
                        stocks.add(stock);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return stocks;
    }

}
