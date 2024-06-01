package com.scaler.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class CreateGroupResponseDTO {
    private String name;
    private List<String> admins;
    private List<String> members;
}
