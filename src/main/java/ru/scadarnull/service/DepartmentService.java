package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.math.BigDecimal;
import java.util.*;

public class DepartmentService {
    private static DepartmentService departmentService;
    private Map<String, Department> departments;

    private DepartmentService() {
        departments = new HashMap<>();
    }

    public static DepartmentService getInstance(){
        if(departmentService == null){
            departmentService = new DepartmentService();
        }
        return departmentService;
    }

    public Department getDepartment(String departmentName){
        //Если отдел существует,то вернуть его, если нет, то создать новый(Как лучше назвать метод?)
        Department department = departments.get(departmentName);
        if(department == null){
            department = new Department(departmentName);
            departments.put(departmentName, department);
        }
        return department;
    }

    public void addEmployeeToDepartment(Employee employee){
        departments.get(employee.getDepartment().getName()).addEmployee(employee);
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments.values());
    }

    public void checkEmployeeTransfer(){
        for(Department department : getDepartments()){
            for(Employee employee : department.getEmployees()){
                if(employee.getSalary().compareTo(department.getAvgSalaryOfEmployees()) <= 0 && department.getEmployees().size() > 1){
                    transferEmployee(employee);
                }
            }
        }
    }

    private void transferEmployee(Employee employee) {
        for(Department department : getDepartments()){
            if(!employee.getDepartment().getName().equals(department.getName()) && department.getAvgSalaryOfEmployees().compareTo(employee.getSalary()) <= 0){
                System.out.println("Сотрудника " + employee.getFullName() +
                        " можно перевести из отдела " + employee.getDepartment().getName() +
                        " в отдел " + department.getName());
            }
        }
    }

}
