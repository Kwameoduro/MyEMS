package model;
import database.EmployeeDatabase;

import java.util.List;
import java.util.stream.Collectors;
public class EmployeeSearch<T> {
    private EmployeeDatabase<T> database;

    public EmployeeSearch(EmployeeDatabase<T> database){
        this.database = database;
    }

    public List<Employee<T>> searchByDepartment(String department){
        if (department == null || department.isBlank()){
            throw new IllegalArgumentException("Department cannot be empty");
        }
        return database.getAllEmployees().values().stream().filter(employee -> employee.getDepartment().equalsIgnoreCase(department)).collect(Collectors.toList());
    }

    public List<Employee<T>> searchByPerformanceRating(double rating){
        if (rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        return database.getAllEmployees().values().stream().filter(employee -> employee.getPerformanceRating() >= rating).collect(Collectors.toList());
    }
    public List<Employee<T>> searchBySalaryRange(double minSalary, double maxSalary){
        if(minSalary < 0 || minSalary > maxSalary){
            throw new IllegalArgumentException("Invalid salary range");
        }
        return database.getAllEmployees().values().stream().filter(employee -> employee.getSalary() >= minSalary && employee.getSalary() <= maxSalary).collect(Collectors.toList());
    }

    public List<Employee<T>> searchByName(String name) {
        if (name == null){
            throw new IllegalArgumentException("Name cannot be empty");
        }
      // Returns a list of matching employees
        return database.getAllEmployees().values().stream().filter(employee -> employee.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        
    }


}
