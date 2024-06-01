package com.scaler.splitwise.controllers;

import com.scaler.splitwise.dtos.*;
import com.scaler.splitwise.models.Group;
import com.scaler.splitwise.models.GroupExpense;
import com.scaler.splitwise.models.Transaction;
import com.scaler.splitwise.services.GroupService;
import org.springframework.web.bind.annotation.*;

import static com.scaler.splitwise.mappers.GroupMapper.groupTocreateGroupResponseDTO;
import static com.scaler.splitwise.mappers.ExpenseMapper.expenseToExpenseResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping("/addGroup")
    public CreateGroupResponseDTO addGroup(@RequestBody CreateGroupRequestDTO createGroupRequestDTO){
        Group group = groupService.addGroup(
                createGroupRequestDTO.getName(),
                createGroupRequestDTO.getAdmins(),
                createGroupRequestDTO.getMembers()
        );
        return groupTocreateGroupResponseDTO(group);
    }
    @PostMapping("/{id}/addExpense")
    public GroupExpenseResponseDTO addGroupExpense(@PathVariable("id") Long groupId , @RequestBody ExpenseRequestDTO expenseRequestDTO){
        GroupExpense groupExpense = groupService.addGroupExpense(
                groupId,
                expenseRequestDTO.getAmount(),
                expenseRequestDTO.getDescription(),
                expenseRequestDTO.getCreatedBy(),
                expenseRequestDTO.getPaidBy(),
                expenseRequestDTO.getHadToPay()
        );
        GroupExpenseResponseDTO groupExpenseResponseDTO = new GroupExpenseResponseDTO();
        groupExpenseResponseDTO.setGroupId(groupId);
        groupExpenseResponseDTO.setExpenseResponseDTO(expenseToExpenseResponseDTO(groupExpense.getExpense()));
        return groupExpenseResponseDTO;
    }
    @PostMapping("/settleUp")
    public SettleUpResponseDTO settleUpGroup(@RequestBody SettleUpGroupRequestDTO settleUpGroupRequestDTO){
        List<Transaction> transactions = groupService.settleUp(settleUpGroupRequestDTO.getGroupId());
        SettleUpResponseDTO settleUpResponseDTO = new SettleUpResponseDTO();
        settleUpResponseDTO.setTransactions(transactions);
        return settleUpResponseDTO;
    }
}
