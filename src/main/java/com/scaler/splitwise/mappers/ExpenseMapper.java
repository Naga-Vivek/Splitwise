package com.scaler.splitwise.mappers;

import com.scaler.splitwise.dtos.ExpenseResponseDTO;
import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class ExpenseMapper {
    public static ExpenseResponseDTO expenseToExpenseResponseDTO(Expense expense){
        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();
        expenseResponseDTO.setCreatedBy(expense.getCreatedBy().getName());
        expenseResponseDTO.setDescription(expense.getDescription());
        expenseResponseDTO.setAmount(expense.getAmount());
        Map<String,Integer> paidByMap = new HashMap<>();
        for(User user : expense.getPaidBy().keySet()){
            String userName = user.getName();
            paidByMap.put(userName, expense.getPaidBy().get(user));
        }
        Map<String,Integer> hadToPayMap = new HashMap<>();
        for(User user : expense.getHadToPay().keySet()){
            String userName = user.getName();
            hadToPayMap.put(userName, expense.getHadToPay().get(user));
        }
        expenseResponseDTO.setPaidBy(paidByMap);
        expenseResponseDTO.setHadToPay(hadToPayMap);
        return expenseResponseDTO;
    }
}
