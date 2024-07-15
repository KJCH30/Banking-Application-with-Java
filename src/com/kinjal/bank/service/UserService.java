package com.kinjal.bank.service;

import com.kinjal.bank.entity.User;
import com.kinjal.bank.repository.UserRepo;

import java.util.List;
import java.util.Map;

public class UserService {
    private UserRepo userRepo = new UserRepo();

    public void printUsers(){
        userRepo.printUsers();
    }

    public User login(String username, String password){
       return userRepo.login(username, password);
    }

    public boolean addNewCustomer(String username, String password, String contact){
        return userRepo.addNewCustomer(username, password, contact);
    }

    public Double checkBankBalance(String userId){
        return userRepo.checkBankBalance(userId);
    }
    public User getUser(String userId){
        return userRepo.getUser(userId);
    }

    public boolean transferAmount(String userId, String payeeUserId, Double amount){
        return userRepo.transferAmount(userId, payeeUserId, amount);
    }
    public void printTransactions(String userId){
        userRepo.printTransactions(userId);
    }
    public void raiseChequeBookReq(String userId) {
        userRepo.raiseChequeBookReq(userId);
    }
    public Map<String, Boolean> getAllChequebookRequest(){
       return userRepo.getAllChequebookRequest();
    }
    public List<String> getUserIdsForChequebookRequest(){
        return userRepo.getUserIdsForChequebookRequest();
    }
    public void approveChequeBook(String userId){
        userRepo.approveChequeBook(userId);
    }
}
