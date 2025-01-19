package com.med.backend.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReq {

    @NotBlank(message = "name should be not blank")
    private String fullName;

    @NotBlank(message = "jobTitle should be not blank")
    private String jobTitle;

    @NotBlank(message = "department should be not blank")
    private String department;

    @FutureOrPresent(message = "date Should Be Present or future")
    @NotNull(message = "Date Should Not Be Null")
    private Date hireDate;

    @NotBlank(message = "employmentStatus should be not blank")
    private String employmentStatus;

    @NotBlank(message = "contactInfo should be not blank")
    private String contactInfo;

    @NotBlank(message = "address should be not blank")
    private String address;
}
