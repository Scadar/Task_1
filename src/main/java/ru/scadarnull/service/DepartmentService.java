package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        Objects.requireNonNull(getDepartmentByName(employee.getDepartment().getName())).addEmployee(employee);
    }

    public Set<Department> getDepartments() {
        return departments;
    }
}
