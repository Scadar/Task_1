package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.math.BigDecimal;
import java.util.*;

public class DepartmentService {
    private static volatile DepartmentService departmentService;
    private Map<String, Department> departments;

    private DepartmentService() {
        departments = new HashMap<>();
    }

    public static DepartmentService getInstance() {
        DepartmentService localInstance = departmentService;
        if (localInstance == null) {
            synchronized (DepartmentService.class) {
                localInstance = departmentService;
                if (localInstance == null) {
                    departmentService = localInstance = new DepartmentService();
                }
            }
        }
        return localInstance;
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

    public Set<Department> getDepartments() {
        return new HashSet<>(departments.values());
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

    public void multiCheckEmployeeTransfer(){
        for(Department department : getDepartments()){
            Map<BigDecimal, List<Employee>> groups = department.getGroupsReadyToTransfer();
            for(Map.Entry<BigDecimal, List<Employee>> group : groups.entrySet()){
                multiTransferEmployee(group, department);
            }
        }
    }

    private void multiTransferEmployee(Map.Entry<BigDecimal, List<Employee>> group, Department currentDepartment) {
        for(Department department : getDepartments()){
            if(!department.getName().equals(currentDepartment.getName()) &&
                department.getAvgSalaryOfEmployees().compareTo(group.getKey()) < 0)
            {
                System.out.println("Сотрудников {");
                for(Employee employee : group.getValue()){
                    System.out.println(employee.getFullName());
                }
                System.out.println("}\nСреднее "+ group.getKey());
                System.out.println("Можно перевести из отдела " + currentDepartment.getName() + " (avg =" + currentDepartment.getAvgSalaryOfEmployees() + ") " +
                " в отдел " + department.getName() + " (avg =" + department.getAvgSalaryOfEmployees() + ")\n\n");
            }
        }
    }

}
