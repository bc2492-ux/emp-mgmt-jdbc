package EmployeeManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {


    private static final String DB_URL = "jdbc:sqlite:employees.db";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }


    public void createTable() {
        // SQL statement to create the table
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT," // Auto-incrementing primary key
                + " name TEXT NOT NULL,"
                + " position TEXT NOT NULL,"
                + " salary REAL NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Execute the statement
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void addEmployee(String name, String position, double salary) {
        String sql = "INSERT INTO employees(name, position, salary) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters using the prepared statement to prevent SQL injection
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();
            System.out.println("Successfully added " + name + " to the database.");
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT id, name, position, salary FROM employees";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }

    public void updateEmployeeSalary(int id, double newSalary) {
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated salary for employee ID " + id + ".");
            } else {
                System.out.println("No employee found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error updating salary: " + e.getMessage());
        }
    }


    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted employee ID " + id + ".");
            } else {
                System.out.println("No employee found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }
}
