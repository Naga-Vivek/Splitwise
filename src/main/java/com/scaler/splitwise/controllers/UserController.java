package com.scaler.splitwise.controllers;

import com.scaler.splitwise.dtos.RegisterUserRequestDTO;
import com.scaler.splitwise.dtos.RegisterUserResponseDTO;
import com.scaler.splitwise.dtos.UserDTO;
import com.scaler.splitwise.models.ResponseStatus;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO requestDTO){
        User user = userService.registerUser(requestDTO.getName() , requestDTO.getPhoneNumber(), requestDTO.getPassword());
        UserDTO userDTO = user.toDto();
        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO();
        responseDTO.setUserDTO(userDTO);
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }
}
