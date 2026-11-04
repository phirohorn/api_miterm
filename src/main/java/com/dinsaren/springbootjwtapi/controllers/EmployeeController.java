package com.dinsaren.springbootjwtapi.controllers;

import com.dinsaren.springbootjwtapi.dto.EmployeeReq;
import com.dinsaren.springbootjwtapi.dto.MessageRes;
import com.dinsaren.springbootjwtapi.models.Employee;
import com.dinsaren.springbootjwtapi.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Employee CRUD")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "List employees, optionally filtered by name, code or email")
    @GetMapping
    public List<Employee> findAll(@RequestParam(required = false) String search) {
        return employeeService.findAll(search);
    }

    @Operation(summary = "Get one employee")
    @GetMapping("/{id}")
    public Employee findById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @Operation(summary = "Create an employee")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@Valid @RequestBody EmployeeReq req) {
        return employeeService.create(req);
    }

    @Operation(summary = "Update an employee")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @Valid @RequestBody EmployeeReq req) {
        return employeeService.update(id, req);
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public MessageRes delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return new MessageRes("Employee deleted successfully");
    }
}
