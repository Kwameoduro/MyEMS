package tests.exceptions;

import database.EmployeeDatabase;
import exceptions.InvalidDepartmentException;
import exceptions.EmployeeNotFoundException;
import model.Employee;
import services.EmployeeSearch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeSearchTest {
    private final EmployeeDatabase<Integer> database = new EmployeeDatabase<>();
    private final EmployeeSearch<Integer> searchManager = new EmployeeSearch<>(database);

    @Test
    void testSearchByInvalidDepartment() {
        Exception exception = assertThrows(InvalidDepartmentException.class, () -> {
            searchManager.searchByDepartment("");
        });
        assertEquals("Department cannot be empty.", exception.getMessage());
    }

    @Test
    void testSearchEmployeeExists() {
        Employee<Integer> emp = new Employee<>(1, "Yaa Fosu", "IT", 6000, 4, 5, true);  //  Fixed type inference
        searchManager.getDatabase().addEmployee(emp);
        assertTrue(searchManager.searchByName("Yaa Fosu").contains(emp));
    }

    @Test
    void testSearchNonExistingEmployeeThrowsException() {
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            searchManager.searchByName("Unknown Name");
        });
        assertEquals("No employees found matching name: Unknown Name", exception.getMessage());
    }

    @Test
    void testSearchBySalaryRange() {
        Employee<Integer> emp1 = new Employee<>(2, "Steelman Bender", "HR", 5000, 3, 4, true);
        Employee<Integer> emp2 = new Employee<>(3, "Ante Alice", "Finance", 8000, 4.5, 6, true);
        searchManager.getDatabase().addEmployee(emp1);
        searchManager.getDatabase().addEmployee(emp2);

        assertEquals(2, searchManager.searchBySalaryRange(4000, 9000).size());
        assertTrue(searchManager.searchBySalaryRange(7000, 9000).contains(emp2));
    }
}
