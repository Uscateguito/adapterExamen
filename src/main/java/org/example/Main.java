package org.example;

import org.example.models.Employee;
import org.example.services.DatabaseService;
import org.example.services.EmployeeHttpClient;
import org.example.services.EmployeeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties properties = new Properties();
        
        try {
            properties.load(Files.newBufferedReader(Paths.get("src/main/resources/config.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String adapterType = properties.getProperty("adapter");
        EmployeeService employeeService;

        if ("http".equalsIgnoreCase(adapterType)) {
            employeeService = new EmployeeHttpClient();
            System.out.println("using the " + adapterType + " adapter");
        } else if ("database".equalsIgnoreCase(adapterType)) {
            employeeService = new DatabaseService();
            System.out.println("using the " + adapterType + " adapter");
        } else {
            System.out.println("Invalid adapter type specified in config.txt");
            return;
        }

        try {
            List<Employee> employees = employeeService.getEmployees();
            System.out.println(employees);
            Employee employee = employeeService.getEmployeeById(1);
            System.out.println(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}