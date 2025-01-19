package com.med.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.med.backend.model.request.EmployeeReq;
import com.med.backend.model.response.EmployeeRes;
import com.med.backend.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ModelMapper modelMapper;

    private EmployeeRes employee;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @BeforeEach
    public void setup() {
        employee = EmployeeRes.builder()
                .id(UUID.fromString("606a61a5-f6c4-4075-86d0-1032eeb017c5"))
                .fullName("John")
                .address("some address")
                .contactInfo("his info")
                .employmentStatus("new")
                .department("it")
                .jobTitle("dev")
                .hireDate(new Date(System.currentTimeMillis() + 86400000))
                .build();
    }

    @Test
    @Order(1)
    void findAll() throws Exception {
        List<EmployeeRes> employeesList = new ArrayList<>();
        employeesList.add(employee);

        given(employeeService.findAll()).willReturn(employeesList);

        ResultActions response = mockMvc.perform(get("/api/v1/employee"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeesList.size())));
    }

    @Test
    @Order(2)
    void save() throws Exception {
        EmployeeReq employeeReq = modelMapper.map(employee, EmployeeReq.class);

        given(employeeService.save(any(EmployeeReq.class))).willReturn(employee);

        ResultActions response = mockMvc.perform(post("/api/v1/employee")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeReq)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is(employee.getFullName())))
                .andExpect(jsonPath("$.address", is(employee.getAddress())))
                .andExpect(jsonPath("$.contactInfo", is(employee.getContactInfo())))
                .andExpect(jsonPath("$.employmentStatus", is(employee.getEmploymentStatus())))
                .andExpect(jsonPath("$.jobTitle", is(employee.getJobTitle())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())));
    }

    @Test
    @Order(3)
    void getByIdEmployeeTest() throws Exception {
        given(employeeService.findOne(employee.getId())).willReturn(employee);

        ResultActions response = mockMvc.perform(get("/api/v1/employee/{id}", employee.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.fullName", is(employee.getFullName())))
                .andExpect(jsonPath("$.address", is(employee.getAddress())))
                .andExpect(jsonPath("$.contactInfo", is(employee.getContactInfo())))
                .andExpect(jsonPath("$.employmentStatus", is(employee.getEmploymentStatus())))
                .andExpect(jsonPath("$.jobTitle", is(employee.getJobTitle())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())));
    }

    @Test
    @Order(4)
    void updateEmployeeTest() throws Exception {
        EmployeeReq updatedReq = modelMapper.map(employee, EmployeeReq.class);
        employee.setFullName("Max");
        employee.setAddress("max@gmail.com");
        given(employeeService.update(employee.getId(), updatedReq)).willReturn(employee);

        ResultActions response = mockMvc.perform(put("/api/v1/employee/{id}", employee.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedReq)));

        response.andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.fullName", is(employee.getFullName())))
//                .andExpect(jsonPath("$.address", is(employee.getAddress())));
    }

    @Test
    @Order(5)
    void deleteEmployee() throws Exception {
        willDoNothing().given(employeeService).delete(employee.getId());

        ResultActions response = mockMvc.perform(delete("/api/v1/employee/{id}", employee.getId()).with(csrf()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
