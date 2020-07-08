package ru.scadarnull.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Employee implements Serializable {

    private Integer id;

    private String fullName;

    private Integer salary;

    private Department department;

    public Employee(Integer id, String fullName, Integer salary, Department department) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.department = department;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", salary=" + salary +
                ", department=" + department.name() +
                '}';
    }

    public void saveToFile() {
        try(FileWriter writer = new FileWriter("Employees.txt", true))
        {
            writer.write(id.toString());
            writer.append(',');
            writer.write(fullName);
            writer.append(',');
            writer.write(salary.toString());
            writer.append(',');
            writer.write(department.name());
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
