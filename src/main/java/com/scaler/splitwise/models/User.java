package com.scaler.splitwise.models;

import com.scaler.splitwise.dtos.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseModel {
    private String name;
    private String phoneNumber;
    private String hashedPassword;

    public UserDTO toDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setName(name);
        userDTO.setId(this.getId());
        return userDTO;
    }
}
