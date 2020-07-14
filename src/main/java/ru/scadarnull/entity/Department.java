package ru.scadarnull.entity;

import ru.scadarnull.service.DepartmentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Department{

    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
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


    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public BigDecimal getAvgSalaryOfEmployees(){
        return DepartmentService.avgOfGroup(employees);
    }

    public List<List<Employee>> getGroupsReadyToTransfer(){
        List<List<Employee>> groupsReadyToTransfer = new ArrayList<>();

        for(int i = 1; i < employees.size(); ++i){
            groupsReadyToTransfer.addAll(combination(new ArrayList<>(employees), i, 0, new ArrayList<>(Arrays.asList(new Employee[i]))));
        }

        return groupsReadyToTransfer;
    }

    private List<List<Employee>> combination(
            List<Employee> array,
            int length,
            int startPosition,
            List<Employee> result)
    {
        List<List<Employee>> groupsReadyToTransfer = new ArrayList<>();
        if (length == 0) {
            if(DepartmentService.avgOfGroup(result).compareTo(getAvgSalaryOfEmployees()) <= 0){
                groupsReadyToTransfer.add(new ArrayList<>(result));
            }
        } else {
            for (int i = startPosition; i <= array.size() - length; i++) {
                result.set(result.size() - length, array.get(i));
                groupsReadyToTransfer.addAll(combination(array, length - 1, i + 1, result));
            }
        }
        return groupsReadyToTransfer;
    }

}
