package com.scaler.splitwise.dtos;

import com.scaler.splitwise.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDTO {
    private UserDTO userDTO;
    private ResponseStatus responseStatus;
}
