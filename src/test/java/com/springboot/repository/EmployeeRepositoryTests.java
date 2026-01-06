package com.springboot.repository;

import com.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("John")
                .lastName("doe")
                .email("john@gmail.com").build();
    }

    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeesList() {
        Employee employee1 = Employee.builder()
                .firstName("Peter")
                .lastName("doe")
                .email("peter@gmail.com").build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployeeObject_whenFindEmail_thenReturnEmployee() {
        employeeRepository.save(employee);

        Employee employee1 = employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employee1).isNotNull();
        assertThat(employee1.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employee1.getLastName()).isEqualTo(employee.getLastName());
    }
}
