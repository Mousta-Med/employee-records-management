package com.med.backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

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
    private UUID id;

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
