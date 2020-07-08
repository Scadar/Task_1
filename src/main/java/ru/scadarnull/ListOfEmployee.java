package ru.scadarnull;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOfEmployee {
    List<Employee> employees;

    public ListOfEmployee() {
        employees = new ArrayList<>();
    }

    public void add(Employee employee){
        employees.add(employee);
    }

    public void remove(Employee employee){
        employees.remove(employee);
    }

    public void saveToFile(){
        for(Employee e : employees){
            e.saveToFile();
        }
    }

    public void readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("Employees.txt")))
        {
            String line = reader.readLine();
            while (line != null) {
                saveEmployeeFromFile(line);
                line = reader.readLine();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void saveEmployeeFromFile(String line) {
        String[] fullInfo = line.split(",");
        employees.add(new Employee(
                Integer.valueOf(fullInfo[0]),
                fullInfo[1],
                Integer.valueOf(fullInfo[2]),
                Department.valueOf(fullInfo[3]))
        );
    }

    public void print(){
        for(Employee e : employees){
            System.out.println(e);
        }
    }
}
