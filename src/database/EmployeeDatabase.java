package database;

import model.Employee;
import exceptions.InvalidSalaryException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidDepartmentException;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDatabase<T> {
    private final Map<T, Employee<T>> employees = new HashMap<>();

    // Add a new employee with validation and exception handling
    public void addEmployee(Employee<T> employee) {
        if (employee.getSalary() < 0) {
            throw new InvalidSalaryException("Salary cannot be negative.");  //  Moved validation outside of try-catch
        }
        employees.put(employee.getEmployeeId(), employee);
        System.out.println(" Employee added successfully.");
        System.out.println("Add Employee operation completed.");
    }

    // Retrieve an employee by ID with correct exception propagation
    public Employee<T> getEmployeeByID(T employeeId) {
        if (!employees.containsKey(employeeId)) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");  //  Exception now properly thrown
        }
        System.out.println("Search Employee operation completed.");
        return employees.get(employeeId);
    }

    // Remove an employee with improved exception handling
    public void removeEmployee(T employeeId) {
        if (!employees.containsKey(employeeId)) {
            throw new EmployeeNotFoundException("Cannot remove: Employee with ID " + employeeId + " does not exist.");
        }
        employees.remove(employeeId);
        System.out.println("Employee removed successfully.");
        System.out.println("Remove Employee operation completed.");
    }

    // **Fixed: Update employee details with exception handling improvements**
    public void updateEmployeeDetails(T employeeId, String field, Object newValue) throws InvalidDepartmentException {
        Employee<T> employee = employees.get(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
        }

        switch (field) {
            case "name":
                employee.setName((String) newValue);
                break;
            case "department":
                if (((String) newValue).isBlank()) {
                    throw new InvalidDepartmentException("Department name cannot be empty.");  // Exception now properly thrown
                }
                employee.setDepartment((String) newValue);
                break;
            case "salary":
                double salary = (Double) newValue;
                if (salary < 0) {
                    throw new InvalidSalaryException("Salary cannot be negative.");
                }
                employee.setSalary(salary);
                break;
            case "performanceRating":
                double rating = (Double) newValue;
                if (rating < 0 || rating > 5) {
                    throw new IllegalArgumentException("Performance rating must be between 0 and 5.");
                }
                employee.setPerformanceRating(rating);
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
        System.out.println(" Employee details updated successfully.");
    }

    // Retrieve all employees
    public Map<T, Employee<T>> getAllEmployees() {
        return employees;
    }
}
