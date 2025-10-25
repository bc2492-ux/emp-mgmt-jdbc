package EmployeeManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static DatabaseService dbService = new DatabaseService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        dbService.createTable();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice (1-5): ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    updateEmployeeSalary();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
            System.out.println("----------------------------------------");
        }
        scanner.close();
    }


    private static void printMenu() {
        System.out.println("\n--- Employee Management System ---");
        System.out.println("1. Add New Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Update Employee Salary");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------");
    }

    private static void addEmployee() {
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Position: ");
            String position = scanner.nextLine();
            double salary = getDoubleInput("Enter Salary: ");

            dbService.addEmployee(name, position, salary);
        } catch (Exception e) {
            System.err.println("Failed to add employee: " + e.getMessage());
        }
    }


    private static void viewAllEmployees() {
        List<Employee> employees = dbService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
            return;
        }

        System.out.println("\n--- All Employees ---");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }


    private static void updateEmployeeSalary() {
        try {
            int id = getIntInput("Enter Employee ID to update: ");
            double newSalary = getDoubleInput("Enter New Salary: ");

            dbService.updateEmployeeSalary(id, newSalary);
        } catch (Exception e) {
            System.err.println("Failed to update salary: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        try {
            int id = getIntInput("Enter Employee ID to delete: ");
            dbService.deleteEmployee(id);
        } catch (Exception e) {
            System.err.println("Failed to delete employee: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return input;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a whole number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                return input;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
