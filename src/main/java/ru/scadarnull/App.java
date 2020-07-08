package ru.scadarnull;

import ru.scadarnull.entity.Department;
import ru.scadarnull.entity.Employee;

import java.io.*;

public class App 
{
    public static void main( String[] args )
    {
//        Employee e1 = new Employee(1, "Ivan ivan", 30000, Department.IT);
//        Employee e2 = new Employee(2, "Ira ira", 32000, Department.FINANCE);
//        Employee e3 = new Employee(3, "Roman roman", 30500, Department.HR);
//        Employee e4 = new Employee(4, "Vasya vasya", 50000, Department.HR);

        ListOfEmployee listOfEmployee = new ListOfEmployee();
//        listOfEmployee.add(e1);
//        listOfEmployee.add(e2);
//        listOfEmployee.add(e3);
//        listOfEmployee.add(e4);

        listOfEmployee.readFromFile();

        listOfEmployee.print();
    }
}
