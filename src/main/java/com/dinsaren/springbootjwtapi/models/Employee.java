package com.dinsaren.springbootjwtapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_code", unique = true)
    private String employeeCode;

    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String position;
    private String department;
    private Double salary;
    private LocalDate hireDate;
    private String address;
    private String photoUrl;

    /** ACT = active, INA = inactive. */
    private String status;
}
