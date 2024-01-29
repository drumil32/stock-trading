package stock_trading.user;

import stock_trading.exception.InsufficientFund;
import stock_trading.exception.InsufficientStockQuantity;
import stock_trading.exception.InvalidInput;
import stock_trading.exception.StockNotAvailable;
import stock_trading.stock.Stock;
import stock_trading.stock.UserStock;
import stock_trading.transaction.OrderType;
import stock_trading.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserPortfolio {
    private List<UserStock> stockList = new ArrayList<>();
    private Integer fund = 0;
    private List<Transaction> transactionList = new ArrayList<>();
    List<UserStock> getStockList(){
        return stockList;
    }
    public Integer getFund() {
        return fund;
    }
    public Integer addFund(Integer fund) throws InvalidInput {
        if( fund<0 )    throw new InvalidInput("fund value can't be negative");
        this.fund += fund;
        return this.fund;
    }
    public void addNewStock(Stock stock, Integer quantity) throws InsufficientFund {
//        if stock is already avilable then ???
        UserStock userStock1 = stockList
                .stream()
                .filter(userStock-> userStock.getStockSymbol().equals(stock.getStockSymbol()))
                .findFirst()
                .orElse(null);
        deducateFund(stock.getPrice() * quantity);

        transactionList.add(new Transaction(new UserStock(stock,quantity), OrderType.BUY));
        if( null==userStock1 ) {
            stockList.add(new UserStock(stock, quantity));
        }else{
            userStock1.addQuantity(quantity,stock.getPrice());
        }
    }

    private void deducateFund(int amount) throws InsufficientFund{
        if( fund<amount )   throw new InsufficientFund();
        fund -= amount;
    }

    public boolean checkFundAvailability(int requiredFund) {
        return fund>=requiredFund;
    }


    public boolean checkStockAndQuantity(String stockSymbol, int quantity) {
        UserStock userStock = stockList
                .stream()
                .filter(stock->stock.getStockSymbol().equals(stockSymbol))
                .findFirst()
                .orElse(null);

        if( null!=userStock && userStock.getQuantity()>=quantity)   return true;

        return false;
    }

    public void removeStockQuantity(Stock stock, int quantity)
            throws StockNotAvailable, InsufficientStockQuantity {
        UserStock userStock = stockList
                .stream()
                .filter(user_stock->user_stock.getStockSymbol().equals(stock.getStockSymbol()))
                .findFirst()
                .orElse(null);

        if( null==userStock )   throw new StockNotAvailable();

        if( userStock.getQuantity()<quantity )  throw new InsufficientStockQuantity();

        if( userStock.getQuantity()>quantity ) {
            userStock.removeQuantity(quantity);
        }else{
            stockList.remove(userStock);
        }
        transactionList.add(new Transaction(new UserStock(stock,quantity), OrderType.SELL));
        this.addFund(quantity*stock.getPrice());
    }
    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
