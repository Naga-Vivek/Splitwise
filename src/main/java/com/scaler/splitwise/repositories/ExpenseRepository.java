package com.scaler.splitwise.repositories;

import com.scaler.splitwise.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value =
            "SELECT * from expense e join expense_paidby_mapping epm on e.id= epm.expense_id where epm.user_key = :userId UNION " +
                    "SELECT * from expense e join expense_hadtopay_mapping ehm on e.id = ehm.expense_id where ehm.user_key = :userId1",nativeQuery = true)
    Set<Expense> findAllByPaidByUserIsOrHadToPayUserIs(@Param("userId") Long userId , @Param("userId1") Long userId1);
}