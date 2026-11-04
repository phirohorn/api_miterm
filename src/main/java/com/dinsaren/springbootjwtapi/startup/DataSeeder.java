package com.dinsaren.springbootjwtapi.startup;

import com.dinsaren.springbootjwtapi.models.*;
import com.dinsaren.springbootjwtapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/** Seeds a login account and demo data, only into tables that are empty. */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Admin@1234"));
            admin.setStatus("ACT");
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
            log.info("Seeded login account: admin / Admin@1234");
        }

        if (productRepository.count() == 0) {
            ProductCategory drinks = categoryRepository.save(new ProductCategory("Drinks"));
            ProductCategory food = categoryRepository.save(new ProductCategory("Food"));
            ProductCategory electronics = categoryRepository.save(new ProductCategory("Electronics"));
            productRepository.saveAll(List.of(
                    product("P001", "Iced Latte", drinks, 2.50, 120, "Cold espresso with milk"),
                    product("P002", "Green Tea", drinks, 1.75, 80, "Fresh brewed green tea"),
                    product("P003", "Fried Rice", food, 3.00, 40, "Khmer-style fried rice"),
                    product("P004", "Chicken Sandwich", food, 3.50, 25, "Grilled chicken sandwich"),
                    product("P005", "Wireless Mouse", electronics, 12.99, 15, "2.4GHz wireless mouse"),
                    product("P006", "USB-C Cable", electronics, 5.49, 60, "1 meter fast charging cable")));
            log.info("Seeded demo products");
        }

        if (employeeRepository.count() == 0) {
            employeeRepository.saveAll(List.of(
                    employee("EMP001", "Sok", "Dara", "MALE", "dara@example.com", "012345678",
                            "Software Engineer", "IT", 850.0),
                    employee("EMP002", "Chan", "Sreymom", "FEMALE", "sreymom@example.com", "098765432",
                            "HR Officer", "Human Resources", 600.0),
                    employee("EMP003", "Kim", "Vuthy", "MALE", "vuthy@example.com", "011223344",
                            "Accountant", "Finance", 700.0)));
            log.info("Seeded demo employees");
        }
    }

    private Product product(String code, String name, ProductCategory category, double price, double stock,
                            String description) {
        Product p = new Product();
        p.setProductCode(code);
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setStockQuantity(stock);
        p.setDescription(description);
        p.setStatus("ACT");
        return p;
    }

    private Employee employee(String code, String firstName, String lastName, String gender, String email,
                              String phone, String position, String department, double salary) {
        Employee e = new Employee();
        e.setEmployeeCode(code);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setGender(gender);
        e.setEmail(email);
        e.setPhoneNumber(phone);
        e.setPosition(position);
        e.setDepartment(department);
        e.setSalary(salary);
        e.setHireDate(LocalDate.now().minusYears(1));
        e.setStatus("ACT");
        return e;
    }
}
