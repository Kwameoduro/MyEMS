package tests.exceptions;

import exceptions.InvalidDepartmentException;
import model.Employee;
import database.EmployeeDatabase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentValidationTest {

    @Test
    void testInvalidDepartmentThrowsException() {
        EmployeeDatabase<Integer> database = new EmployeeDatabase<>();
        database.addEmployee(new Employee<>(1, "Yaw Peter", "IT", 6000, 3, 4, true));

        //  Ensure the exception is properly thrown
        assertThrows(InvalidDepartmentException.class, () -> {
            database.updateEmployeeDetails(1, "department", "");
        });
    }

    @Test
    void testValidDepartmentDoesNotThrowException() {
        EmployeeDatabase<Integer> database = new EmployeeDatabase<>();
        database.addEmployee(new Employee<>(2, "Kofi John", "HR", 5500, 3, 5, true));

        // Ensuring valid department updates do not trigger exceptions
        assertDoesNotThrow(() -> database.updateEmployeeDetails(2, "department", "Finance"));
    }
}
