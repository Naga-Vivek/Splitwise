package com.scaler.splitwise.services;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.Transaction;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.repositories.ExpenseRepository;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.passwordhashing.PasswordHashingStrategy;
import com.scaler.splitwise.strategies.settleup.SettleUpExpensesStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordHashingStrategy passwordHashingStrategy;
    private ExpenseRepository expenseRepository;
    private SettleUpExpensesStrategy settleUpExpensesStrategy;

    public UserService(UserRepository userRepository, PasswordHashingStrategy passwordHashingStrategy,ExpenseRepository expenseRepository , SettleUpExpensesStrategy settleUpExpensesStrategy) {
        this.userRepository = userRepository;
        this.passwordHashingStrategy = passwordHashingStrategy;
        this.expenseRepository = expenseRepository;
        this.settleUpExpensesStrategy = settleUpExpensesStrategy;
    }

    public User registerUser(String name , String phoneNumber , String password){
        String hashedPassword = passwordHashingStrategy.hash(password);
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setHashedPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return  savedUser;
    }

    public Expense addExpense(int amount , String description , String userName , Map<String,Integer> paidByUsers , Map<String , Integer> hadToPayUsers){
       Expense expense = new Expense();
       expense.setAmount(amount);
       expense.setDescription(description);
       User createdBy = userRepository.findByName(userName);
       expense.setCreatedBy(createdBy);

       Map<User,Integer> paidBy = new HashMap<>();
       for(String userName1 : paidByUsers.keySet()){
           User user = userRepository.findByName(userName1);
           paidBy.put(user,paidByUsers.get(userName1));
       }

        Map<User,Integer> hadToPay = new HashMap<>();
        for(String userName1 : hadToPayUsers.keySet()){
            User user = userRepository.findByName(userName1);
            hadToPay.put(user,hadToPayUsers.get(userName1));
        }

        expense.setPaidBy(paidBy);
        expense.setHadToPay(hadToPay);
        Expense savedExpense = expenseRepository.save(expense);
        return savedExpense;
    }
    public List<Transaction> settleUp(Long userId){
        Set<Expense> expenses = expenseRepository.findAllByPaidByUserIsOrHadToPayUserIs(userId,userId);
        List<Transaction> transactions = settleUpExpensesStrategy.settle(expenses);
        return  transactions;
    }
}
