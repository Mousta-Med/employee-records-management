package com.med.backend.model.request;

import com.med.backend.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {

    @NotBlank(message = "username should be not blank")
    private String username;

    @NotBlank(message = "password should be not blank")
    private String password;

    private Role role;
}
