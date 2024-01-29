package stock_trading.user;

import stock_trading.exception.InsufficientFund;
import stock_trading.exception.InsufficientStockQuantity;
import stock_trading.exception.InvalidInput;
import stock_trading.exception.StockNotAvailable;
import stock_trading.stock.*;
import stock_trading.transaction.OrderType;
import stock_trading.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private UserPortfolio userPortfolio;

    User(String username,String password){
        this.username = username;
        this.password = password;
        this.userPortfolio = new UserPortfolio();
    }

    String getUsername(){
        return username;
    }
    String getPassword(){
        return password;
    }
    UserPortfolio getUserPortfolio(){return userPortfolio;}
    @Override
    public String toString() {
        return "User{" +"\n"+
                "username='" + username + '\'' + "\n" +
                ", password='" + password + '\'' + "\n" +
                ", userPortfolio " + userPortfolio + "\n" +
                '}';
    }


}
