package ru.scadarnull;

import ru.scadarnull.entity.Department;
import ru.scadarnull.service.DepartmentService;
import ru.scadarnull.service.EmployeeService;

public class App
{
    public static void main( String[] args )
    {
        if(args.length < 2){
            System.out.println("Введены некорректные параметры программы, нужно ввести входной и выходной файл");
        }else{
            String inputFile = args[0];
            String outputFile = args[1];

            DepartmentService departmentService = DepartmentService.getInstance();
            EmployeeService employeeService = new EmployeeService(inputFile, outputFile);

            if(employeeService.readFromFile()){
                employeeService.print();
            }else{
                System.out.println("Файл для чтения не найден");
            }

            for(Department department : departmentService.getDepartments()){
                System.out.println(department.getName() + " = " + department.getAvgSalaryOfEmployees());
            }

            //System.out.println(departmentService.groupCheckEmployeeTransfer());
            if(!employeeService.saveGroupsToFile()){
                System.out.println("Файл для записи не найден и не может быть создан");
            }
            //departmentService.checkEmployeeTransfer();
        }

    }
}
