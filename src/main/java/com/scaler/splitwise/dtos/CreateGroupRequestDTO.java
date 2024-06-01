package com.scaler.splitwise.dtos;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateGroupRequestDTO {
    private String name;
    private List<String> admins;
    private List<String> members;
}
