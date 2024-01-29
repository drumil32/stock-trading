package stock_trading.exception;

public class InvalidStockSymbol extends RuntimeException {
    public InvalidStockSymbol(){
        super("stock with the given symbol is not exists");
    }
    public InvalidStockSymbol(String message){
        super(message);
    }
}
