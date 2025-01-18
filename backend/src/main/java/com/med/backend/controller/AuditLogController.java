package com.med.backend.controller;

import com.med.backend.model.response.AuditLogRes;
import com.med.backend.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(name = "/api/v1/log")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogRes>> findAll() {
        return ResponseEntity.ok(auditLogService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AuditLogRes>> getLogsByEmployeeId(@PathVariable UUID id) {
        return ResponseEntity.ok(auditLogService.getLogsByEmployeeId(id));
    }
}
