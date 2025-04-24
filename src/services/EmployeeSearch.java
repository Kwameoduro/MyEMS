package model;
import database.EmployeeDatabase;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidDepartmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeSearch<T> {
    private final EmployeeDatabase<T> database;

    public EmployeeSearch(EmployeeDatabase<T> database) {
        this.database = database;
    }

    // Search employees by department with exception handling
    public List<model.Employee<T>> searchByDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new InvalidDepartmentException("Department cannot be empty.");
        }
        return database.getAllEmployees().values()
                .stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Search employees by name with **corrected exception handling**
    public List<model.Employee<T>> searchByName(String name) {
        List<model.Employee<T>> results = database.getAllEmployees().values()
                .stream()
                .filter(employee -> employee.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found matching name: " + name);  //  Exception now properly thrown
        }

        return results;
    }

    // Search employees by salary range with exception handling
    public List<model.Employee<T>> searchBySalaryRange(double minSalary, double maxSalary) {
        if (minSalary < 0 || minSalary > maxSalary) {
            throw new IllegalArgumentException("Invalid salary range.");
        }
        return database.getAllEmployees().values()
                .stream()
                .filter(employee -> employee.getSalary() >= minSalary && employee.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    public EmployeeDatabase<T> getDatabase() {
        return database;
    }
}
