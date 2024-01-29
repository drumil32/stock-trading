package stock_trading.exception;

public class InsufficientStockQuantity extends RuntimeException{
    public InsufficientStockQuantity(){
        super("you don't have these many stocks of specified symbol");
    }
    public InsufficientStockQuantity(String message){
        super(message);
    }
}
