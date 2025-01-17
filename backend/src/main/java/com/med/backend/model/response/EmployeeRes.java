package com.med.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRes {

    private String fullName;
    private String jobTitle;
    private String department;
    private Date hireDate;
    private String employmentStatus;
    private String contactInfo;
    private String address;

}
