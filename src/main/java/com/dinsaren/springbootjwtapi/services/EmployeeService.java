package com.dinsaren.springbootjwtapi.services;

import com.dinsaren.springbootjwtapi.dto.EmployeeReq;
import com.dinsaren.springbootjwtapi.exception.ConflictException;
import com.dinsaren.springbootjwtapi.exception.NotFoundException;
import com.dinsaren.springbootjwtapi.models.Employee;
import com.dinsaren.springbootjwtapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> findAll(String search) {
        if (StringUtils.hasText(search)) {
            return employeeRepository.search(search.trim());
        }
        return employeeRepository.findAllByOrderByIdDesc();
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found"));
    }

    @Transactional
    public Employee create(EmployeeReq req) {
        if (employeeRepository.existsByEmployeeCode(req.employeeCode().trim())) {
            throw new ConflictException("Employee code '" + req.employeeCode() + "' already exists");
        }
        Employee employee = new Employee();
        apply(employee, req);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Integer id, EmployeeReq req) {
        Employee employee = findById(id);
        if (employeeRepository.existsByEmployeeCodeAndIdNot(req.employeeCode().trim(), id)) {
            throw new ConflictException("Employee code '" + req.employeeCode() + "' already exists");
        }
        apply(employee, req);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void delete(Integer id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        // Flush now so a foreign-key failure surfaces here as a 409, not after the response.
        employeeRepository.flush();
    }

    private void apply(Employee employee, EmployeeReq req) {
        employee.setEmployeeCode(req.employeeCode().trim());
        employee.setFirstName(req.firstName().trim());
        employee.setLastName(req.lastName().trim());
        employee.setGender(req.gender());
        employee.setDateOfBirth(req.dateOfBirth());
        employee.setEmail(req.email().trim());
        employee.setPhoneNumber(req.phoneNumber().trim());
        employee.setPosition(req.position());
        employee.setDepartment(req.department());
        employee.setSalary(req.salary());
        employee.setHireDate(req.hireDate());
        employee.setAddress(req.address());
        employee.setPhotoUrl(req.photoUrl());
        employee.setStatus(StringUtils.hasText(req.status()) ? req.status() : "ACT");
    }
}
