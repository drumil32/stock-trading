package stock_trading.exception;

public class StockNotAvailable extends RuntimeException{
    public StockNotAvailable(){
        super("stock with the given symbol is not present in your portfolio");
    }
    public StockNotAvailable(String message){
        super(message);
    }
}
