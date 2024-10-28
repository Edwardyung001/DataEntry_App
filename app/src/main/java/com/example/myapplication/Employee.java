package com.example.myapplication;

public class Employee {
    private String id;
    private String name;
    private String role;
    private String salary;

    public Employee(String id, String name, String role, String salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getSalary() {
        return salary;
    }
}
