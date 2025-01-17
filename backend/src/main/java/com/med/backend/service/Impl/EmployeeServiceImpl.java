package com.med.backend.service.Impl;

import com.med.backend.exception.ResourceNotFoundException;
import com.med.backend.model.entity.Employee;
import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;
import com.med.backend.repository.EmployeeRepository;
import com.med.backend.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeRes save(EmployeeReq employeeReq) {
        Employee Employee = modelMapper.map(employeeReq, Employee.class);
        return modelMapper.map(employeeRepository.save(Employee), EmployeeRes.class);
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
        return modelMapper.map(employeeRepository.save(existingEmployee), EmployeeRes.class);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not found with this: " + id));
        employeeRepository.deleteById(id);
    }

}

