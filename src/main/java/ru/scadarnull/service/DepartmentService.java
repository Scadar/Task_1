package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public void groupCheckEmployeeTransfer(){
        for(Department department : getDepartments()){
            List<List<Employee>> groups = department.getGroupsReadyToTransfer();
            for(List<Employee> group : groups){
                groupTransferEmployee(group, department);
            }
        }
    }

    private void groupTransferEmployee(List<Employee> group, Department currentDepartment) {
        for(Department department : getDepartments()){
            if(!department.getName().equals(currentDepartment.getName()) &&
                department.getAvgSalaryOfEmployees().compareTo(avgOfGroup(group)) < 0)
            {
                System.out.println("Сотрудников {");
                for(Employee employee : group){
                    System.out.println(employee.getFullName());
                }
                System.out.println("}\nСредняя зп = "+ avgOfGroup(group) + "\n" +
                "Можно перевести из отдела " + currentDepartment.getName() +
                " (avg =" + currentDepartment.getAvgSalaryOfEmployees() + ") " +
                " в отдел " + department.getName() + " (avg =" + department.getAvgSalaryOfEmployees() + ")\n");
            }
        }
    }

    private BigDecimal avgOfGroup(List<Employee> group){
        BigDecimal sum = new BigDecimal(0);
        for(Employee e : group){
            sum = sum.add(e.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(group.size()), 2, RoundingMode.HALF_UP);
    }
}
