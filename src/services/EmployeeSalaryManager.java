package services;
import database.EmployeeDatabase;

import java.util.List;
import java.util.stream.Collectors;

//Class for managing employee salaries and performing analytics
public class EmployeeSalaryManager<T> {
    private EmployeeDatabase<T> database;

    public EmployeeSalaryManager(EmployeeDatabase<T> database){
        this.database = database;
    }

    //Method to give salary raises to employees with high performance ratings.

    public void giveSalaryRaise(double performanceThreshold, double raisePercentage){
        database.getAllEmployees().values().forEach(employee -> {
            if(employee.getPerformanceRating() >= performanceThreshold){
                double newSalary =  Double.parseDouble(String.valueOf(employee.getSalary() * (1 + raisePercentage / 100)));
                employee.setSalary(newSalary);
            }
        });
    }

    //Method to retrieve the top 5 highest paid employees.
    public List<model.Employee<T>> getTopPaidEmployees(int topN){
        return database.getAllEmployees().values().stream().sorted(new model.EmployeeComparators.EmployeeSalaryComparator<>()).limit(topN).collect(Collectors.toList());
    }

    //Method to calculate the average salary in a specific department
    public double calculateAverageSalary(String department){
        return database.getAllEmployees().values().stream().filter(employee -> employee.getDepartment().equalsIgnoreCase(department)).mapToDouble(model.Employee::getSalary).average().orElse(0.0);
    }
}
