package com.dinsaren.springbootjwtapi.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/** Request body for creating and updating an employee. */
public record EmployeeReq(
        @NotBlank(message = "Employee code is required")
        @Size(max = 50, message = "Employee code must be at most 50 characters")
        String employeeCode,

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be at most 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be at most 100 characters")
        String lastName,

        @Pattern(regexp = "^(MALE|FEMALE|OTHER)?$", message = "Gender must be MALE, FEMALE or OTHER")
        String gender,

        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9 ]{8,15}$", message = "Phone number must be 8 to 15 digits")
        String phoneNumber,

        @Size(max = 100, message = "Position must be at most 100 characters")
        String position,

        @Size(max = 100, message = "Department must be at most 100 characters")
        String department,

        @PositiveOrZero(message = "Salary cannot be negative")
        Double salary,

        @PastOrPresent(message = "Hire date cannot be in the future")
        LocalDate hireDate,

        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,

        String photoUrl,

        @Pattern(regexp = "^(ACT|INA)?$", message = "Status must be ACT or INA")
        String status) {
}
