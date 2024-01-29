package stock_trading;

import com.fasterxml.jackson.databind.ObjectMapper;
import stock_trading.stock.Stock;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockWriter {
    List<Stock> stockList;
    static String folderPath = "./stock_info";
    public StockWriter(List<Stock> stockList){
        this.stockList = stockList;
    }
    public void startWriting() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(stockList.size());

        stockList.forEach(
            stock -> {
                Runnable task = () -> {
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        Integer change = (int) (Math.random() * 10);
                        if( 0==Math.round(Math.random()) ) {
                            if( stock.getPrice()>change )
                                change = -change;
                        }
                        stock.changePrice(change);
                        String json = objectMapper.writeValueAsString(stock);
                        objectMapper.writeValue(new File(folderPath + "/" + stock.getStockSymbol() + ".json"), stock);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
                scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
            }
        );

        // Add a shutdown hook to ensure the scheduler is shut down when the program terminates
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down scheduler...");
            scheduler.shutdown();
            try {
                // Wait for the scheduler to finish its current tasks or until timeout (e.g., 10 seconds)
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("Scheduler did not terminate in the specified time.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
