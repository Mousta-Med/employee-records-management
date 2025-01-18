package com.med.backend.service.Impl;

import com.med.backend.exception.ResourceNotFoundException;
import com.med.backend.model.entity.AuditLog;
import com.med.backend.model.entity.Employee;
import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;
import com.med.backend.repository.AuditLogRepository;
import com.med.backend.repository.EmployeeRepository;
import com.med.backend.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final AuditLogRepository auditLogRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper, AuditLogRepository auditLogRepository) {
        this.modelMapper = modelMapper;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void setAuditLog(String action, UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuditLog auditLog = new AuditLog();
        auditLog.setEmployeeId(id);
        auditLog.setAction(action);
        auditLog.setModifiedBy(authentication.getName());
        auditLogRepository.save(auditLog);
    }


    @Override
    public EmployeeRes save(EmployeeReq employeeReq) {
        Employee employee = modelMapper.map(employeeReq, Employee.class);
        setAuditLog("Save Employee", employee.getId());
        return modelMapper.map(employeeRepository.save(employee), EmployeeRes.class);
    }

    @Override
    public List<EmployeeRes> findAll() {
        return employeeRepository.findAll().stream().map(Employee -> modelMapper.map(Employee, EmployeeRes.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeRes findOne(UUID id) {
        return employeeRepository.findById(id)
                .map(Employee -> modelMapper.map(Employee, EmployeeRes.class)).orElseThrow(() -> new ResourceNotFoundException("Employee Not found with this: " + id));
    }

    @Override
    public EmployeeRes update(UUID id, EmployeeReq employeeReq) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not found with this: " + id));
        BeanUtils.copyProperties(employeeReq, existingEmployee);
        existingEmployee.setId(id);
        setAuditLog("Update Employee", id);
        return modelMapper.map(employeeRepository.save(existingEmployee), EmployeeRes.class);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not found with this: " + id));
        employeeRepository.deleteById(id);
        setAuditLog("Delete Employee", id);
    }

}

