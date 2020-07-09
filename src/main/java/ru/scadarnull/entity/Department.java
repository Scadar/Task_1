package ru.scadarnull.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Department{

    private String name;
    private Set<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new HashSet<>();
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public BigDecimal getAvgSalaryOfEmployees(){
        BigDecimal sum = new BigDecimal(0);
        for(Employee e : employees){
            sum = sum.add(e.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(employees.size()));
    }
}
