package database;
import model.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDatabase<T> {
    private Map<T, Employee<T>> employees = new HashMap<>();

    //add a new employee to the database
    public void addEmployee(Employee<T> employee){
        employees.put(employee.getEmployeeId(), employee);
    }

    //retrieve an employee by ID
    public Employee<T> getEmployeeByID(T employeeId){
        Employee<T> employee = employees.get(employeeId);
        if(employee == null){
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found.");
        }
        return employee;
    }


    //Remove an employee from the database by ID
    public void removeEmployee(T employeeId){
        employees.remove(employeeId);
    }

    public void updateEmployeeDetails(T employeeId, String field, Object newValue){
        Employee<T> employee = employees.get(employeeId);
        if (employee != null){
            switch (field) {
                case "name":
                    employee.setName((String) newValue);
                    break;
                case "department":
                    employee.setDepartment((String) newValue);
                    break;
                case "salary":
                    employee.setSalary((Double) newValue);
                    break;
                case "performanceRating":
                    employee.setPerformanceRating((Double) newValue);
                    break;
                case "yearsOfExperience":
                    employee.setYearsOfExperience((Integer) newValue);
                    break;
                case "isActive":
                    employee.setActive((Boolean) newValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        }
    }
    public Map<T, Employee<T>> getAllEmployees(){
        return employees;
    }
}
