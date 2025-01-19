package com.med.backend.model.response;

import com.med.backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {

    private UUID id;
    private String username;
    private String password;
    private Role role;
}
