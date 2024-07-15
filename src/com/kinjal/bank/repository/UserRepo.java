package com.kinjal.bank.repository;

import com.kinjal.bank.entity.Transaction;
import com.kinjal.bank.entity.User;

import java.time.LocalDate;
import java.util.*;

public class UserRepo {
   private static Set<User> users = new HashSet<>();
   private static List<Transaction> transactions = new ArrayList<>();
   Map<String, Boolean> chequeBookRequest = new HashMap<>();

   static {
       User user1 = new User("admin", "admin", "9092388721", "admin", 0.0);
       User user2 = new User("faizal", "pass@123", "8972718281", "user", 10000.0);
       User user3 = new User("jish", "jish@123", "8567718281", "user", 12000.0);
       User user4 = new User("user4", "user@123", "8567018281", "user", 12000.0);

       users.add(user1);
       users.add(user2);
       users.add(user3);
       users.add(user4);
   }

   public void printUsers(){
       System.out.println(users);
   }

   public User login(String username, String password){
       List<User> finalList = users.stream()
               .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
               .toList();

       if (!finalList.isEmpty()){
           return finalList.get(0);
       }else{
           return null;
       }
   }

    public boolean addNewCustomer(String username, String password, String contact){
       User user =  new User(username, password, contact, "user", 500.0);
       return users.add(user);
    }

    public Double checkBankBalance(String userId){
      List<User> result = users.stream().filter(user -> user.getUsername().equals(userId)).toList();
      if (!result.isEmpty()){
          return result.get(0).getAccountBalance();
      }else {
          return null;
      }
    }

    public User getUser(String userId){
       List<User> result = users.stream().filter(user -> user.getUsername().equals(userId)).toList();
       if (!result.isEmpty()){
           return result.get(0);
       }
       return null;
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount){
       boolean isDebit = debit(userId, amount, payeeUserId);
       boolean isCredit = credit(payeeUserId, amount, userId);
       return isDebit && isCredit;
    }
    private boolean debit(String userId, Double amount, String payeeUserId){
       User user = getUser(userId);
       Double accountBalance = user.getAccountBalance();
       users.remove(user); // Since the Set doesn't allow update operation that's why  to update the balance, we need to first remove it and then add again
       Double finalBalance = accountBalance - amount;
       user.setAccountBalance(finalBalance);

        Transaction transaction =  new Transaction(
                LocalDate.now(),
                amount,
                payeeUserId,
                userId,
                "Debit",
                accountBalance,
                finalBalance
        );
        System.out.println(transaction);
        transactions.add(transaction);
       return users.add(user);
    }
    private boolean credit (String payeeUserId, Double amount, String userId){
        User user = getUser(payeeUserId);
        Double accountBalance = user.getAccountBalance();
        users.remove(user); // Since the Set doesn't allow update operation that's why  to update the balance, we need to first remove it and then add again
        Double finalBalance = accountBalance + amount;
        user.setAccountBalance(finalBalance);

        Transaction transaction =  new Transaction(
                LocalDate.now(),
                amount,
                userId,
                payeeUserId,
                "Credit",
                accountBalance,
                finalBalance
        );
        System.out.println(transaction);
        transactions.add(transaction);
        return users.add(user);
    }

    public void printTransactions(String userId){
       List<Transaction> filteredTransactions =  transactions.stream().filter(transaction -> transaction.getTransactionPerformedBy().equals(userId)).toList();

        System.out.println("Date \t\t User-ID \t Amount \t Type \t Initial Balance \t Final Balance");
        System.out.println("---------------------------------------------------------------------");
        for (Transaction t : filteredTransactions){
            System.out.println(t.getTransactionDate()
            +"\t\t" + t.getTransactionId()
            +"\t\t" + t.getTransactionAmount()
            +"\t\t" + t.getTransactionType()
                    +"\t\t" + t.getInitialBalance()
                    +"\t\t" + t.getFinalBalance()
            );
        }
        System.out.println("---------------------------------------------------------------------");
    }

    public void raiseChequeBookReq(String userId) {
       chequeBookRequest.put(userId, false);
        System.out.println("Your request of a new chequebook is taken :-)");
    }
    public Map<String, Boolean> getAllChequebookRequest(){
       return chequeBookRequest;
    }
    public List<String> getUserIdsForChequebookRequest(){
       List<String> userIds = new ArrayList<>();
       for (Map.Entry<String, Boolean> entry : chequeBookRequest.entrySet()){
           if (!entry.getValue()){
               userIds.add(entry.getKey());
           }
       }
       return userIds;
    }
    public void approveChequeBook(String userId){
       if (chequeBookRequest.containsKey(userId)){
           chequeBookRequest.put(userId, true);
           System.out.println("Chequebook request for "+ userId+" is approved :-)");
       }
    }
}
