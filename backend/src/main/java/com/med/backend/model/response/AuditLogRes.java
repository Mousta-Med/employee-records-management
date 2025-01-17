package com.med.backend.model.response;

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
public class AuditLogRes {

    private UUID employeeId;
    private String action;
    private UUID modifiedBy;
    private LocalDateTime timestamp = LocalDateTime.now();
}
