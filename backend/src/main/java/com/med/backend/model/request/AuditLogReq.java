package com.med.backend.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogReq {

    @NotNull(message = "employee id Should Not Be Null")
    private UUID employeeId;

    @NotBlank(message = "action should be not blank")
    private String action;

    @NotNull(message = "modifiedBy Should Not Be Null")
    private String modifiedBy;

    private LocalDateTime timestamp = LocalDateTime.now();
}
