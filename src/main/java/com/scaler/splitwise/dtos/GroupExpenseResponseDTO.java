package com.scaler.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupExpenseResponseDTO {
    Long groupId;
    ExpenseResponseDTO expenseResponseDTO;
}
