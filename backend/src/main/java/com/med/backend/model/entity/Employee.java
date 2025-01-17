package com.med.backend.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private UUID employeeId;

    @Column(nullable = false)
    private String fullName;

    private String jobTitle;
    private String department;

    @Temporal(TemporalType.DATE)
    private Date hireDate;

    private String employmentStatus;
    private String contactInfo;
    private String address;
}
