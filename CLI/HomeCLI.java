package stock_trading.CLI;

import stock_trading.StockReader;
import stock_trading.exception.UserAlreadyExists;
import stock_trading.stock.Stock;
import stock_trading.user.User;
import stock_trading.user.UserManager;
import stock_trading.user.UserSession;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HomeCLI {
    private Scanner scanner;

    public HomeCLI(){
        this.scanner = new Scanner(System.in);
    }
    public void start() throws IOException {
        while(true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    generateUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    seeStockList();
                    break;
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("----- Stock Trading System -----");
        System.out.println("1. Press 1 for user generation");
        System.out.println("2. Press 2 for login");
        System.out.println("3. Press 3 for see stock list");
        System.out.println("0. Press 0 to exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        int choice = -1;

        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

        return choice;
    }

    private void generateUser() throws IOException {
        System.out.println("----- User Generation -----");
        System.out.print("Enter the username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter the password: ");
        String password = scanner.nextLine();

        // Create a new User instance with the entered username and password
        try {
            User newUser = UserManager.generateUser(userName, password);
            System.out.println("User generated successfully!");
        }catch (UserAlreadyExists e){
            System.out.println(e.getMessage());
        }

    }

    private void login() throws IOException {
        System.out.println("----- Login -----");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Perform login verification (customize this part based on your requirements)
        UserSession userSession = verifyLogin(username, password);
        if (null!=userSession) {
            System.out.println("Login successful! Welcome, " + username + "!");
            UserCLI userCLI = new UserCLI(userSession);
            userCLI.start();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private UserSession verifyLogin(String username, String password) {
        return UserManager.verifyUser(username,password);
    }

    private void seeStockList() throws IOException {
        // Implement logic to display the stock list
        System.out.println("Stock list:");
        List<Stock> stockList = StockReader.readStocksFromJsonFiles();
        for (Stock stock : stockList) {
            System.out.println(stock);
        }
        goBackToMenu();
    }

    private void goBackToMenu() throws IOException {
        System.out.println("please press enter to go back to the menu!!");
        scanner.nextLine();
    }

}
