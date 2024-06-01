package com.scaler.splitwise.strategies.settleup;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.Transaction;

import java.util.List;
import java.util.Set;

public interface SettleUpExpensesStrategy {
    List<Transaction> settle(Set<Expense> expenses);
}
