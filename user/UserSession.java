package stock_trading.user;

import stock_trading.StockReader;
import stock_trading.exception.*;
import stock_trading.stock.Stock;
import stock_trading.stock.UserStock;

import java.util.List;

public class UserSession {
    private User user;

    public UserSession(User user) {
        this.user = user;
    }

    public void logout() {
        user = null;
    }

    public List<UserStock> getUserStocks() {
        return user.getUserPortfolio().getStockList();
    }

    public Integer getUserFund() {
        return user.getUserPortfolio().getFund();
    }

    public Integer addFund(Integer amount) throws InvalidInput {
        return user.getUserPortfolio().addFund(amount);
    }

    public boolean buyStock(String stockSymbol, int quantity) throws InsufficientFund, InvalidStockSymbol {
        Stock stock = StockReader.readTheStockBySymbol(stockSymbol);

        if (null == stock) return false;

        boolean success = user.getUserPortfolio().checkFundAvailability(stock.getPrice() * quantity);

        if (!success) throw new InsufficientFund();

        user.getUserPortfolio().addNewStock(stock, quantity);
        System.out.println("Stock with symbol :- " + stock.getStockSymbol() +
                "\n is bought at the price :- " + stock.getPrice() +
                "\n quantity of stock is :- " + quantity +
                "\n total amount which you spent is :- " + stock.getPrice() * quantity
        );
        return true;
    }

    public boolean sellStock(String stockSymbol, int quantity)
            throws InsufficientStockQuantity, StockNotAvailable, InvalidStockSymbol {
        Boolean flag = user.getUserPortfolio().checkStockAndQuantity(stockSymbol, quantity);

        if (false == flag) throw new InsufficientStockQuantity();

        Stock stock = StockReader.readTheStockBySymbol(stockSymbol);
        if (null == stock) return false;

        user.getUserPortfolio().removeStockQuantity(stock, quantity);

        System.out.println("Stock with symbol :- " + stock.getStockSymbol() +
                "\n is sold at the price :- " + stock.getPrice() +
                "\n quantity of stock is :- " + quantity +
                "\n total amount which you get is :- " + stock.getPrice() * quantity
        );
        return true;
    }

    public void showTransactionHistory() {
        if (user.getUserPortfolio().getTransactionList().isEmpty()) {
            System.out.println("No transaction");
        } else {
            user.getUserPortfolio().getTransactionList().stream().forEach(
                    transaction -> {
                        System.out.println(transaction);
                        System.out.println("================================");
                    }
            );
        }
    }
}
