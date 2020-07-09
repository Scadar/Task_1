package ru.scadarnull.entity;

import java.math.BigDecimal;

public class Employee{

    private Integer id;
    private String fullName;
    private BigDecimal salary;
    private Department department;

    public Employee(Integer id, String fullName, BigDecimal salary, Department department) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.department = department;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }

    public String toStringForFile() {
        return
                id.toString() + ',' +
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

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
