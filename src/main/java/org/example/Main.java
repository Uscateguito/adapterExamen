package org.example;

import org.example.models.Employee;
import org.example.services.DatabaseService;
import org.example.services.HttpService;
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
            properties.load(Files.newBufferedReader(Paths.get("adapter/src/main/resources/config.txt")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String adapterType = properties.getProperty("adapter");
        EmployeeService employeeService;

        // La base de datos se configura en el archivo config.txt y puede ser http o database
        if ("http".equalsIgnoreCase(adapterType)) {
            employeeService = new HttpService();
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



