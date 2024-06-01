package com.scaler.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.HashMap;
import java.util.Map;

// created_by
// Expense : User
//    1    :  1
//    M     :   1
@Getter
@Setter
@ToString
@Entity
public class Expense extends BaseModel {
    private int amount;
    // curent_class TO attribute_class
    // Expense TO User
    //   M     TO  1
    @ManyToOne
    private User createdBy;

    private String description;

    @ElementCollection
    @CollectionTable(name = "expense_paidby_mapping", joinColumns = @JoinColumn(name = "expense_id") )
    @Column(name="paid_amount")
    @MapKeyJoinColumn(name = "user_key")
    private Map<User, Integer> paidBy;

    @ElementCollection
    @CollectionTable(name = "expense_hadtopay_mapping", joinColumns = @JoinColumn(name = "expense_id") )
    @Column(name="had_to_pay_amount")
    @MapKeyJoinColumn(name = "user_key")
    private Map<User, Integer> hadToPay;
}

/*
@ElementCollection is a powerful tool for managing non-entity collections within your JPA entities.
The @ElementCollection annotation is used to define a collection of instances of a basic type or an embeddable class.
Unlike @OneToMany or @ManyToMany, which work with related entities, @ElementCollection is specifically designed for non-entity collections.
It allows you to map a collection of simple types (such as strings, integers, etc.) or a collection of embeddable elements (classes annotated with @Embeddable).
 */