package org.example.services;

import org.example.models.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees() throws Exception;
    Employee getEmployeeById(int id) throws Exception;
}