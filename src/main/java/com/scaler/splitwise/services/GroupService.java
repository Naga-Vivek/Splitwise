package com.scaler.splitwise.services;

import com.scaler.splitwise.dtos.ExpenseRequestDTO;
import com.scaler.splitwise.models.*;
import com.scaler.splitwise.repositories.GroupExpenseRepository;
import com.scaler.splitwise.repositories.GroupRepository;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.settleup.SettleUpExpensesStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private GroupExpenseRepository groupExpenseRepository;
    private SettleUpExpensesStrategy settleUpExpensesStrategy;
    private UserRepository userRepository;
    private UserService userService;

    public GroupService(GroupRepository groupRepository, GroupExpenseRepository groupExpenseRepository, SettleUpExpensesStrategy settleUpExpensesStrategy,
                        UserRepository userRepository,UserService userService) {
        this.groupRepository = groupRepository;
        this.groupExpenseRepository = groupExpenseRepository;
        this.settleUpExpensesStrategy = settleUpExpensesStrategy;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    public Group addGroup(String name , List<String> adminlist , List<String> memberlist){
        Group group = new Group();
        group.setName(name);
        List<User> admins = new ArrayList<>();
        List<User> members = new ArrayList<>();
        for(String adminName : adminlist){
            User user = userRepository.findByName(adminName);
            admins.add(user);
            members.add(user);
        }
        for(String memberName : memberlist){
            User user = userRepository.findByName(memberName);
            members.add(user);
        }
        group.setAdmins(admins);
        group.setMembers(members);
        Group savedGroup = groupRepository.save(group);
        return  savedGroup;
    }
    public GroupExpense addGroupExpense(Long groupId, int amount , String description , String userName , Map<String,Integer> paidByUsers , Map<String , Integer> hadToPayUsers){
        Expense expense =  userService.addExpense(amount,description,userName,paidByUsers,hadToPayUsers);

        Group group = groupRepository.findById(groupId).get();

        GroupExpense groupExpense = new GroupExpense();
        groupExpense.setGroup(group);
        groupExpense.setExpense(expense);
        GroupExpense savedGroupExpense = groupExpenseRepository.save(groupExpense);
        return savedGroupExpense;
    }
    public List<Transaction> settleUp(Long groupId){
        Group group = groupRepository.findById(groupId).get();
        List<GroupExpense> groupExpenses = groupExpenseRepository.findAllByGroup(group);
        Set<Expense> expenses = new HashSet<>();
        for(GroupExpense groupExpense : groupExpenses){
            expenses.add(groupExpense.getExpense());
        }
        List<Transaction> transactions = settleUpExpensesStrategy.settle(expenses);
        return transactions;

    }
}
