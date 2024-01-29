package stock_trading.CLI;

import stock_trading.StockReader;
import stock_trading.exception.*;
import stock_trading.stock.Stock;
import stock_trading.stock.UserStock;
import stock_trading.user.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserCLI {
    private final UserSession userSession;
    private final Scanner scanner;

    public UserCLI(UserSession userSession) {
        this.userSession = userSession;
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        while (true) {
            printOptions();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    logout();
                    return;
                case 2:
                    showStockList(true);
                    break;
                case 3:
                    showUserPortfolio(true);
                    break;
                case 4:
                    buyStock();
                    break;
                case 5:
                    sellStock();
                    break;
                case 6:
                    addFund();
                    break;
                case 7:
                    showTransactionHistory();
                    break;
                case 0:
                    System.out.println("Exiting User CLI. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() throws IOException {
        userSession.showTransactionHistory();
        goBackToMenu();
    }

    private void addFund() throws IOException {
        System.out.println("please provide amount :- ");
        Integer amount = Integer.parseInt(scanner.nextLine());
        try {
            Integer totalFund = userSession.addFund(amount);
            System.out.println("Fund is added Now You have Rs." + totalFund);
        }catch (InvalidInput e){
            System.out.println(e.getMessage());
        }
        goBackToMenu();
    }

    private void buyStock() throws IOException {
        showStockList(false);
        // Ask user for stock symbol
        System.out.print("Enter the stock symbol you want to buy: ");
        String stockSymbol = scanner.nextLine();
        int quantity;
        while(true) {
            // Ask user for quantity
            System.out.print("Enter the quantity you want to buy: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please provide number!!");
            }
        }

        try {
            // Call the userSession method to handle the stock purchase
            boolean success = userSession.buyStock(stockSymbol, quantity);
            if (!success) {
                System.out.println("Stock purchase failed. Please check the stock symbol and quantity.");
            }
        }catch (InsufficientFund | InvalidStockSymbol e){
            System.out.println(e.getMessage());
        }
        goBackToMenu();
    }

    private void sellStock() throws IOException {
        showUserPortfolio(false);
        // Ask user for stock symbol
        System.out.print("Enter the stock symbol which you want to sell: ");
        String stockSymbol = scanner.nextLine();
        int quantity = 0;
        while(true) {
            // Ask user for quantity
            try {
                System.out.println("Enter the quantity you want to sell: ");
                quantity = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("please provide number");
            }
        }

        try {
            // Call the userSession method to handle the stock purchase
            boolean success = userSession.sellStock(stockSymbol, quantity);

            if (!success) {
                System.out.println("Stock sell failed. Please check the stock symbol and quantity.");
            }
        }catch (StockNotAvailable | InsufficientStockQuantity | InvalidStockSymbol e){
            System.out.println(e.getMessage());
        }
        goBackToMenu();
    }

    private void printOptions() {
        System.out.println("----- User Options -----");
        System.out.println("1. Logout");
        System.out.println("2. Show Stock List");
        System.out.println("3. Show Portfolio");
        System.out.println("4. Buy Stock");
        System.out.println("5. Sell Stock");
        System.out.println("6. Add Funds");
        System.out.println("7. View Transaction History");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {

        Integer choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }

    }

    private void logout() {
        System.out.println("Logging out...");
        userSession.logout();
        System.out.println("Logout successful!");
    }

    private boolean showStockList(boolean flag) throws IOException {
        List<Stock> stockList = StockReader.readStocksFromJsonFiles();
        if (stockList.isEmpty()) {
            System.out.println("No stocks available.");
            if(flag)    goBackToMenu();
            return false;
        } else {
            System.out.println("----- Regular Stock List -----");
            for (Stock stock : stockList) {
                System.out.println(stock);
            }
        }
        if(flag)    goBackToMenu();
        return true;
    }
    private boolean showUserPortfolio(boolean flag) throws IOException {

        List<UserStock> userStocks = userSession.getUserStocks();
        List<Stock> stockList = new ArrayList<>();

        userStocks.stream().forEach(userStock -> {
            stockList.add(StockReader.readTheStockBySymbol(userStock.getStockSymbol()));
        });

        System.out.println("Your Fund is :- "+userSession.getUserFund());
        if (userStocks.isEmpty()) {
            System.out.println("No stocks in your portfolio.");
            if(flag)    goBackToMenu();
            return false;
        } else {
            System.out.println("----- Your Stock Portfolio -----");
            for(int i=0 ; i<userStocks.size() ; i++){
                int currentPrice = stockList.get(i).getPrice();
                int profit = (currentPrice-userStocks.get(i).getPrice())*userStocks.get(i).getQuantity();
                System.out.println(userStocks.get(i) + "\tcurrentPrice :- "+currentPrice+"\tprofit :- "+profit);
            }
        }
        if(flag)    goBackToMenu();
        return true;
    }

    private void goBackToMenu() throws IOException {

        System.out.println("please press enter to go back to the menu!!");
        scanner.nextLine();

    }
}