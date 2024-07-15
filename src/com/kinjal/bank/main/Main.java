package com.kinjal.bank.main;

import com.kinjal.bank.entity.User;
import com.kinjal.bank.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Main main = new Main();
    static UserService userService = new UserService();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        while (true){
            System.out.println("Enter your username");
            String username = input.next();
            System.out.println("Enter your password");
            String password = input.next();

//        System.out.println("Username entered: "+username + " Password entered: "+password);
            User user = userService.login(username, password);
            if (user != null && user.getRole().equals("admin")){
                main.initAdmin();
            } else if (user != null && user.getRole().equals("user")) {
                main.initCustomer(user);
            } else {
                System.out.println("Login Failed");
            }
        }
    }

    private void initAdmin(){
        boolean flag =  true;

        while (flag){
            System.out.println("1. Exit/Logout");
            System.out.println("2. Create a customer account");
            System.out.println("3. See All transaction details");
            System.out.println("4. Check Bank Balance of a User");
            System.out.println("5. Approve pending chequebook requests");
            int selectedOption = input.nextInt();

            switch (selectedOption){
                case 1:
                    flag = false;
                    System.out.println("Admin Logged Out");
                    break;
                case 2:
                    main.addNewCustomer();
                    break;
                case 3:
                    System.out.println("Enter a user-ID");
                    String userID = input.next();
                    main.printTransactions(userID);
                    break;
                case 4:
                    System.out.println("Enter a user-ID");
                    userID = input.next();
                    Double accountBalance = checkBankBalance(userID);
                    System.out.println("Account Balance of "+userID+" is Rs. "+accountBalance);
                    break;
                case 5:
                    List<String> userIds = getUserIdsForChequebookRequest();
                    if (userIds.isEmpty()){
                        System.out.println("There are no pending requests");
                    }else {
                        System.out.println("Please select user id from below: ");
                        System.out.println(userIds);

                        String userId = input.next();
                        approveChequeBook(userId);
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    private void approveChequeBook(String userId){
        userService.approveChequeBook(userId);
    }
    private List<String> getUserIdsForChequebookRequest(){
        return userService.getUserIdsForChequebookRequest();
    }
    private void addNewCustomer(){
        System.out.println("Enter username");
        String username = input.next();

        System.out.println("Enter password");
        String password = input.next();

        System.out.println("Enter Contact Number");
        String contact = input.next();

        boolean addedCustomer = userService.addNewCustomer(username, password, contact);

        if (addedCustomer){
            System.out.println("Customer account created successfully!! :-)");
        }else{
            System.out.println("Customer account creation failed :-(");
        }
    }

    private void initCustomer(User user){
        boolean flag = true;

        while (flag){
            System.out.println("1. Exit/Logout");
            System.out.println("2. Check Current Balance");
            System.out.println("3. Fund Transfer");
            System.out.println("4. See All Transactions");
            System.out.println("5. Raise Chequebook request");
            int selectedOption = input.nextInt();

            switch (selectedOption){
                case 1:
                    flag = false;
                    System.out.println("Customer Logged Out");
                    break;
                case 2:
                    Double balance = main.checkBankBalance(user.getUsername());
                    if (balance != null){
                        System.out.println("Your Bank Balance is -> "+balance);
                    }else {
                        System.out.println("Something went wrong");
                    }
                    break;
                case 3:
                    main.fundTransfer(user);
                    break;
                case 4:
                    main.printTransactions(user.getUsername());
                    break;
                case 5:
                    String userId = user.getUsername();
                    Map<String, Boolean> map = getAllChequebookRequest();
                    if (map.containsKey(userId) && map.get(userId)){
                        System.out.println("You have already raised a request and it has been approved.");
                    }else if (map.containsKey(userId) && !map.get(userId)){
                        System.out.println("You have already raised a request and it is currently under process");
                    }else {
                        raiseChequeBookReq(user.getUsername());
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    private Map<String, Boolean> getAllChequebookRequest(){
        return userService.getAllChequebookRequest();
    }
    private void raiseChequeBookReq(String userId){
        userService.raiseChequeBookReq(userId);
    }
    private void printTransactions(String userId){
        userService.printTransactions(userId);
    }
    private void fundTransfer(User userDetails){
        System.out.println("Enter payee account user-ID/ username");
        String payeeAccountId = input.next();

        User user = getUser(payeeAccountId);

        if (user != null){
            System.out.println("Please enter an amount to transfer");
            Double amount = input.nextDouble();

            Double userAccountBalance = checkBankBalance(userDetails.getUsername());

            if (userAccountBalance >= amount){
                boolean result = userService.transferAmount(userDetails.getUsername(), payeeAccountId, amount);

                if (result){
                    System.out.println("Fund transfer is now complete :-)");
                }else {
                    System.out.println("Transfer not done X");
                }
            }else {
                System.out.println(
                        "Insufficient Balance :-( "+ userAccountBalance
                );
            }
        }else {
            System.out.println("Please enter a valid username/user-ID");
        }
    }
    private User getUser(String userId){
        return userService.getUser(userId);
    }
    private Double checkBankBalance(String userId){
        return userService.checkBankBalance(userId);
    }
}
