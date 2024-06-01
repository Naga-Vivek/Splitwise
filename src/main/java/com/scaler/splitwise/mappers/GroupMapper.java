package com.scaler.splitwise.mappers;

import com.scaler.splitwise.dtos.CreateGroupResponseDTO;
import com.scaler.splitwise.models.Group;
import com.scaler.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class GroupMapper {
    public static CreateGroupResponseDTO groupTocreateGroupResponseDTO(Group group){
        CreateGroupResponseDTO createGroupResponseDTO = new CreateGroupResponseDTO();
        createGroupResponseDTO.setName(group.getName());
        List<String> admins = new ArrayList<>();
        for(User admin : group.getAdmins()){
            admins.add(admin.getName());
        }
        List<String> members = new ArrayList<>();
        for(User member : group.getMembers()){
            members.add(member.getName());
        }
        createGroupResponseDTO.setAdmins(admins);
        createGroupResponseDTO.setMembers(members);
        return createGroupResponseDTO;
    }
}
