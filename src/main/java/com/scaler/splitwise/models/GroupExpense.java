package com.scaler.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GroupExpense extends BaseModel {
    @ManyToOne
    private Group group;

    @OneToOne
    private Expense expense;
}
// GroupExpense : Group
//     1           1
//     M           1
// GroupExpense : Expense
//     1           1
//     1           1
//        group_expenses
//   group_id   |   expense_id
//     1        |     2
//     1        |     4