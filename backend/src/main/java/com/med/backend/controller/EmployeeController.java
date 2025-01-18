package com.med.backend.controller;

import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;
import com.med.backend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeRes>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRes> findOne(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.findOne(id));
    }


    @PostMapping
    public ResponseEntity<EmployeeRes> save(@RequestBody @Valid EmployeeReq userDto) {
        return ResponseEntity.ok(employeeService.save(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRes> update(@PathVariable UUID id, @RequestBody @Valid EmployeeReq userDto) {
        return ResponseEntity.ok(employeeService.update(id, userDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
