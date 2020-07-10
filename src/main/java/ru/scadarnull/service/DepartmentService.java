package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.math.BigDecimal;
import java.util.*;

public class DepartmentService {
    private static DepartmentService departmentService;
    private Set<Department> departments;

    private DepartmentService() {
        departments = new HashSet<>();
    }

    public static DepartmentService getInstance(){
        if(departmentService == null){
            departmentService = new DepartmentService();
        }
        return departmentService;
    }

    public Department getDepartment(String departmentName){
        //Если отдел существует,то вернуть его, если нет, то создать новый(Как лучше назвать метод?)
        Department department = getDepartmentByName(departmentName);
        if(department == null){
            department = new Department(departmentName);
            departments.add(department);
        }
        return department;
    }


    private Department getDepartmentByName(String departmentName) {
        for(Department d : departments){
            if(d.getName().equalsIgnoreCase(departmentName)){
                return d;
            }
        }
        return null;
    }

    public void addEmployeeToDepartment(Employee employee){
        getDepartmentByName(employee.getDepartment().getName()).addEmployee(employee);
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void checkEmployeeTransfer(){
        for(Department department : departments){
            for(Employee employee : department.getEmployees()){
                if(employee.getSalary().compareTo(department.getAvgSalaryOfEmployees()) <= 0 && department.getEmployees().size() > 1){
                    transferEmployee(employee);
                }
            }
        }
    }

    private void transferEmployee(Employee employee) {
        for(Department department : departments){
            if(department.getAvgSalaryOfEmployees().compareTo(employee.getSalary()) <= 0 && !employee.getDepartment().getName().equals(department.getName())){
                System.out.println("Сотрудника " + employee.getFullName() +
                        " можно перевести из отдела " + employee.getDepartment().getName() +
                        " в отдел " + department.getName());
            }
        }
    }

}
