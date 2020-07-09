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
        employees = new ArrayList<>();
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void add(Employee employee){
        employees.add(employee);
    }

    public void remove(Employee employee){
        employees.remove(employee);
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
            int countOfEmployee = 1;
            while (line != null) {
                saveEmployeeFromFile(line, countOfEmployee++);
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

    private void saveEmployeeFromFile(String line, int countOfEmployee) {
        String[] fullInfo = line.split(",");
        if(checkEmployeeValid(fullInfo)){

            Employee employee = new Employee(
                    Integer.valueOf(fullInfo[0]),
                    fullInfo[1],
                    new BigDecimal(fullInfo[2]),
                    DepartmentService.getInstance().getDepartment(fullInfo[3]));

            DepartmentService.getInstance().addEmployeeToDepartment(employee);
            employees.add(employee);
        }else{
            System.out.println("Ошибка при чтении сотрудника в строке = " + countOfEmployee + " (" + inputFile + ")");
        }

    }

    private boolean checkEmployeeValid(String[] fullInfo) {
        try{
            if(
                    fullInfo.length != 4 ||
                    fullInfo[1].trim().length() == 0 ||
                    fullInfo[3].trim().length() == 0
            ){
                return false;
            }

            Integer.parseInt(fullInfo[0]);
            Double.parseDouble(fullInfo[2]); //Как проверить на BigDecimal?

        }catch (NumberFormatException | NullPointerException nfe){
            return false;
        }

        return true;
    }

    public void print(){
        for(Employee e : employees){
            System.out.println(e);
        }
    }
}
