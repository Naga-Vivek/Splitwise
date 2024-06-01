package com.scaler.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group extends BaseModel {
    // GROUP : USER
    //   1       M
    //   M       1
    //    M to M
    @ManyToMany
    private List<User> admins;

    @ManyToMany
    private List<User> members;

    private String name;
}
