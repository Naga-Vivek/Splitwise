package com.scaler.splitwise.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ExpenseRequestDTO {
    private int amount;
    private String createdBy;
    private String description;
    private Map<String, Integer> paidBy;
    private Map<String, Integer> hadToPay;
}
