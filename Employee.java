package EmployeeManager;

public class Employee {

    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }


    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-20s | Position: %-15s | Salary: $%.2f",
                id, name, position, salary);
    }
}
