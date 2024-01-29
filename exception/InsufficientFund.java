package stock_trading.exception;

public class InsufficientFund extends RuntimeException {
    public InsufficientFund(){
        super("fund is not sufficient");
    }
    public InsufficientFund(String message){
        super(message);
    }
}
