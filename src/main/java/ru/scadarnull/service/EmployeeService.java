package ru.scadarnull.service;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private String inputFile;
    private String outputFile;
    private List<Employee> employees;

    public EmployeeService(String inputFile, String outputFile) {
        this.employees = new ArrayList<>();
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void add(Employee employee){
        employees.add(employee);
    }

    public boolean saveToFile(){

        try(FileWriter writer = new FileWriter(inputFile, true))
        {
            for(Employee e : employees) {
                writer.write(e.toStringForFile());
                writer.flush();
            }
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
            return false;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public boolean readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile)))
        {
            String line = reader.readLine();
            int employeeLine = 1;
            while (line != null) {
                readEmployeeFromFile(line, employeeLine++);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
            return false;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("Успешно получено " + employees.size() + " cотрудников (");
        for(Department department : DepartmentService.getInstance().getDepartments()){
            System.out.println(department.getName() + " = " + department.getEmployees().size());
        }
        System.out.println(")");

        return true;
    }

    private void readEmployeeFromFile(String line, int employeeLine) {
        String[] fullInfo = line.split(",");
        List<ErrorStatus> errorMessages = checkEmployeeValid(fullInfo);
        if(errorMessages.size() == 0){

            DepartmentService departmentService = DepartmentService.getInstance();
            Department department = departmentService.getDepartment(fullInfo[2]);

            Employee employee = new Employee(
                    fullInfo[0],
                    new BigDecimal(fullInfo[1]),
                    department);

            departmentService.addEmployeeToDepartment(employee);
            add(employee);
        }else{
            System.out.println("Ошибка при чтении сотрудника в строке = " + employeeLine);
            System.out.println(errorMessages.toString());
        }

    }

    private List<ErrorStatus> checkEmployeeValid(String[] fullInfo) {
        List<ErrorStatus> result = new ArrayList<>();
        try{
            if(fullInfo.length != 3){
                result.add(ErrorStatus.INVALID_NUMBER_OF_ARGS);
            }else{
                if(fullInfo[0].trim().length() == 0){
                    result.add(ErrorStatus.INVALID_NAME);
                }
                if(fullInfo[2].trim().length() == 0){
                    result.add(ErrorStatus.INVALID_DEPARTMENT);
                }
                BigDecimal salary = new BigDecimal(fullInfo[1]);
                if(salary.compareTo(BigDecimal.valueOf(0)) <= 0 || salary.scale() > 2){
                    result.add(ErrorStatus.INVALID_SALARY);
                }
            }
        }catch (NumberFormatException | NullPointerException nfe){
            result.add(ErrorStatus.INVALID_SALARY);
        }
        return result;
    }

    public void print(){
        for(Employee e : employees){
            System.out.println(e);
        }
    }

    public boolean saveGroupsToFile(){
        try(FileWriter writer = new FileWriter(outputFile, false))
        {
            String text = DepartmentService.getInstance().groupCheckEmployeeTransfer();
            writer.write(text);
            writer.flush();
        }
        catch (FileNotFoundException fileNotFoundException){
            System.out.println(fileNotFoundException.getMessage());
            return false;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }
}

enum ErrorStatus {
    INVALID_NAME,
    INVALID_SALARY,
    INVALID_DEPARTMENT,
    INVALID_NUMBER_OF_ARGS
}
