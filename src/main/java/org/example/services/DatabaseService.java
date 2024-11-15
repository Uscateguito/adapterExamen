package org.example.services;

import org.example.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService implements EmployeeService {

    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_URL = System.getenv("DB_URL");
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Employee findById(int id) throws Exception {
        String sql = "SELECT * FROM Employee WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
            return null;
        }
    }
    
    private List<Employee> findAll() throws Exception {
        String sql = "SELECT * FROM Employee";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
            return employees;
        }
    }

    @Override
    public List<Employee> getEmployees() throws Exception {
        return findAll();
    }

    @Override
    public Employee getEmployeeById(int id) throws Exception {
        return findById(id);
    }
}
