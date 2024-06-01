package com.scaler.splitwise.strategies.settleup;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.Transaction;
import com.scaler.splitwise.models.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GreedySettleUpExpensesStrategy implements SettleUpExpensesStrategy{
    class Record implements Comparable<Record>{
        User user;
        int pendingAmount;

        public Record(User user, int pendingAmount) {
            this.user = user;
            this.pendingAmount = pendingAmount;
        }

        @Override
        public int compareTo(Record o) {
            return this.pendingAmount-o.pendingAmount;
        }
    }
    @Override
    public List<Transaction> settle(Set<Expense> expenses) {
        Map<User,Integer> extraMoney = new HashMap<>();

        for(Expense expense : expenses){
            for(User user : expense.getPaidBy().keySet()){
                if(!extraMoney.containsKey(user)){
                    extraMoney.put(user,0);
                }
                extraMoney.put(user ,extraMoney.get(user) + expense.getPaidBy().get(user));
            }
            for(User user : expense.getHadToPay().keySet()){
                if(!extraMoney.containsKey(user)){
                    extraMoney.put(user,0);
                }
                extraMoney.put(user, extraMoney.get(user) - expense.getHadToPay().get(user));
            }
        }

        Queue<Record> positiveQueue = new PriorityQueue<>(Collections.reverseOrder()); //max heap
        Queue<Record> negativeQueue = new PriorityQueue<>(); //min heap

        for(User user : extraMoney.keySet()){
            if(extraMoney.get(user) > 0){ // paid Extra
                positiveQueue.add(new Record(user, extraMoney.get(user)));
            }
            else if(extraMoney.get(user) < 0){ // paid Lesser
                negativeQueue.add(new Record(user,extraMoney.get(user)));
            }
        }
        
        List<Transaction> transactions = new ArrayList<>();
        while(!positiveQueue.isEmpty() && !negativeQueue.isEmpty()){
            Record firstPositive = positiveQueue.poll();
            Record firstNegative = negativeQueue.poll();

            //A : 150
            //B : -100
            //B pays A : 100, and B gets settled up
            //A is remained with 150+(-100) = 50 , add to positive queue
            if(firstPositive.pendingAmount > Math.abs(firstNegative.pendingAmount)){
                transactions.add(
                        new Transaction(firstNegative.user.toDto(), firstPositive.user.toDto(), Math.abs(firstNegative.pendingAmount))
                );
                positiveQueue.add(new Record(firstPositive.user, firstPositive.pendingAmount+ firstNegative.pendingAmount));
            }
            //A : 150
            //B : -200
            //B pays A : 150 , A gets settled up
            //B is remained with 150 + (-200) = -50 , add to negative queue
            else if(firstPositive.pendingAmount < Math.abs(firstNegative.pendingAmount)){
                transactions.add(
                        new Transaction(firstNegative.user.toDto(), firstPositive.user.toDto(), firstPositive.pendingAmount)
                );
                negativeQueue.add(new Record(firstNegative.user, firstPositive.pendingAmount+ firstNegative.pendingAmount));
            }
            else{
                transactions.add(
                        new Transaction(firstNegative.user.toDto(), firstPositive.user.toDto(),firstPositive.pendingAmount)
                );
            }
        }
        return transactions;
    }
}
