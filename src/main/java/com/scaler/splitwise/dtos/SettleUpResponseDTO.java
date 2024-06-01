package com.scaler.splitwise.dtos;

import com.scaler.splitwise.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SettleUpResponseDTO {
    private List<Transaction> transactions;
}
