package com.med.backend.service.Impl;

import com.med.backend.exception.ResourceNotFoundException;
import com.med.backend.model.entity.AuditLog;
import com.med.backend.model.entity.Employee;
import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;
import com.med.backend.repository.AuditLogRepository;
import com.med.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeReq employeeReq;
    private EmployeeRes employeeRes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        employee = Employee.builder()
                .id(UUID.randomUUID())
                .fullName("John Doe")
                .address("123 Street")
                .contactInfo("123456789")
                .employmentStatus("Active")
                .department("IT")
                .jobTitle("Developer")
                .hireDate(new Date())
                .build();

        employeeReq = new EmployeeReq();
        BeanUtils.copyProperties(employee, employeeReq);

        employeeRes = new EmployeeRes();
        BeanUtils.copyProperties(employee, employeeRes);
    }

    @Test
    @Order(1)
    void testSave() {
        when(modelMapper.map(employeeReq, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeRes.class)).thenReturn(employeeRes);
        when(authentication.getName()).thenReturn("test_user");
        EmployeeRes result = employeeService.save(employeeReq);
        assertNotNull(result);
        assertEquals(employeeRes, result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    @Order(2)
    void testFindAll() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        when(modelMapper.map(employee, EmployeeRes.class)).thenReturn(employeeRes);

        List<EmployeeRes> result = employeeService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeRes, result.get(0));
    }

    @Test
    @Order(3)
    void testFindOne() {
        UUID id = employee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeRes.class)).thenReturn(employeeRes);

        EmployeeRes result = employeeService.findOne(id);

        assertNotNull(result);
        assertEquals(employeeRes, result);
    }

    @Test
    @Order(4)
    void testFindOneNotFound() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.findOne(id));
    }

    @Test
    @Order(5)
    void testUpdate() {
        UUID id = employee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeRes.class)).thenReturn(employeeRes);
        when(authentication.getName()).thenReturn("test_user");

        EmployeeRes result = employeeService.update(id, employeeReq);

        assertNotNull(result);
        assertEquals(employeeRes, result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    @Order(6)
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.update(id, employeeReq));
    }

    @Test
    @Order(7)
    void testDelete() {
        UUID id = employee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(authentication.getName()).thenReturn("test_user");
        employeeService.delete(id);
        verify(employeeRepository, times(1)).deleteById(id);
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }

    @Test
    @Order(8)
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.delete(id));
    }
}
