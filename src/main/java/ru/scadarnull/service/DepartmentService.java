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

    public String groupCheckEmployeeTransfer(){
        StringBuilder result = new StringBuilder();
        for(Department department : getDepartments()){
            List<List<Employee>> groups = department.getGroupsReadyToTransfer();
            for(List<Employee> group : groups){
                result.append(groupTransferEmployee(group, department));
            }
        }
        return result.toString();
    }

    private StringBuilder groupTransferEmployee(List<Employee> group, Department currentDepartment) {
        StringBuilder result = new StringBuilder();
        for(Department department : getDepartments()){
            if(!department.getName().equals(currentDepartment.getName()) &&
                department.getAvgSalaryOfEmployees().compareTo(avgOfGroup(group)) < 0)
            {
                result.append("Сотрудников {\n");
                for(Employee employee : group){
                    result.append(employee.getFullName()).append("\n");
                }
                result.append("}\nСредняя зп по группе = ")
                        .append(avgOfGroup(group))
                        .append("\n")
                        .append("Можно перевести из отдела ")
                        .append(currentDepartment.getName())
                        .append(" (avg до = ")
                        .append(currentDepartment.getAvgSalaryOfEmployees())
                        .append(" ) (avg после = ")
                        .append(avgOfGroup(currentDepartment.getEmployees(), group))
                        .append(")\nв отдел \n")
                        .append(department.getName())
                        .append(" (avg до = ")
                        .append(department.getAvgSalaryOfEmployees()).append(" ) (avg после = ")
                        .append(department.getAvgSalaryOfEmployees().add(avgOfGroup(group)).divide(BigDecimal.valueOf(2)))
                        .append(")\n\n");
            }
        }
        return result;
    }

    private BigDecimal avgOfGroup(List<Employee> group, List<Employee> except){
        List<Employee> result = new ArrayList<>();
        for(Employee employee : group){
            if(!except.contains(employee)){
                result.add(employee);
            }
        }
        return avgOfGroup(result);
    }

    public static BigDecimal avgOfGroup(List<Employee> group){
        BigDecimal sum = new BigDecimal(0);
        for(Employee e : group){
            sum = sum.add(e.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(group.size()), 2, RoundingMode.HALF_UP);
    }


}
