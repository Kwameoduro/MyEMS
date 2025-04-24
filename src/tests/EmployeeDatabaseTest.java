package tests;

import database.EmployeeDatabase;
import exceptions.InvalidSalaryException;
import exceptions.EmployeeNotFoundException;
import model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDatabaseTest {
    private final EmployeeDatabase<String> database = new EmployeeDatabase<>();

    @Test
    void testAddEmployeeWithInvalidSalary() {
        Exception exception = assertThrows(InvalidSalaryException.class, () -> {
            Employee<String> employee = new Employee<>("001", "Kofi Peter", "HR", -5000, 4.8, 5, true);
            database.addEmployee(employee);
        });

        assertEquals("Salary cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetEmployeeByID_NotFound() {
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            database.getEmployeeByID("999");
        });

        assertEquals("Employee with ID 999 not found.", exception.getMessage());
    }
}
