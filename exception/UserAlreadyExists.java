package stock_trading.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(){
        super("user with the given username is already exists");
    }
    public UserAlreadyExists(String message){
        super(message);
    }
}
