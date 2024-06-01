package com.scaler.splitwise.controllers;

import com.scaler.splitwise.dtos.*;
import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.ResponseStatus;
import com.scaler.splitwise.models.Transaction;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.scaler.splitwise.mappers.ExpenseMapper.expenseToExpenseResponseDTO;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO requestDTO){
        User user = userService.registerUser(requestDTO.getName() , requestDTO.getPhoneNumber(), requestDTO.getPassword());
        UserDTO userDTO = user.toDto();
        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO();
        responseDTO.setUserDTO(userDTO);
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/settleUp")
    public SettleUpResponseDTO settleUp(@RequestBody SettleUpUserRequestDTO settleUpUserRequestDTO){
        List<Transaction> transactions = userService.settleUp(settleUpUserRequestDTO.getUserId());
        SettleUpResponseDTO settleUpResponseDTO = new SettleUpResponseDTO();
        settleUpResponseDTO.setTransactions(transactions);
        return settleUpResponseDTO;
    }

    @PostMapping("/addExpense")
    public ExpenseResponseDTO addExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO){
        Expense expense = userService.addExpense(
                expenseRequestDTO.getAmount(),
                expenseRequestDTO.getDescription(),
                expenseRequestDTO.getCreatedBy(),
                expenseRequestDTO.getPaidBy(),
                expenseRequestDTO.getHadToPay()
        );
        return  expenseToExpenseResponseDTO(expense);
    }
}
