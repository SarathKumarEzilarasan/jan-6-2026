package com.springboot.service;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee))
                .willReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenExistingEmail_whenSave_thenThrowsException() {
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeesList() {
        Employee employee1 = Employee.builder()
                .firstName("Peter")
                .lastName("doe")
                .email("peter@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        List<Employee> employees = employeeService.getAllEmployees();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

}
