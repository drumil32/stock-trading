package stock_trading.transaction;

import stock_trading.stock.UserStock;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final UserStock userStock;
    private final Date date;
    private final OrderType orderType;
    public Transaction(UserStock userStock,OrderType orderType){
        Calendar calendar = Calendar.getInstance();
        this.userStock = userStock;
        this.date = calendar.getTime();
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "stock=" + userStock +"\n" +
                "date=" + date +"\n" +
                "type=" + orderType;
    }
}
