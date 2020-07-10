package ru.scadarnull.entity;

import java.math.BigDecimal;

public class Employee{

    private String fullName;
    private BigDecimal salary;
    private Department department;

    public Employee(String fullName, BigDecimal salary, Department department) {
        this.fullName = fullName;
        this.salary = salary;
        this.department = department;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }

    public String toStringForFile() {
        return
                fullName + ',' +
                salary.toString() + ',' +
                department.toString() + '\n';
    }

    public Department getDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getFullName() {
        return fullName;
    }
}
