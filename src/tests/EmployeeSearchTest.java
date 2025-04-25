package tests;

import database.EmployeeDatabase;
import exceptions.InvalidDepartmentException;
import model.Employee;
import services.EmployeeSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeSearchTest {
    private final EmployeeDatabase<String> database = new EmployeeDatabase<>();
    private final EmployeeSearch<String> searchManager = new EmployeeSearch<>(database);

    @Test
    void testSearchByInvalidDepartment() {
        Exception exception = assertThrows(InvalidDepartmentException.class, () -> {
            searchManager.searchByDepartment("");
        });
        assertEquals("Department cannot be empty.", exception.getMessage());
    }

    @Test
    void testSearchEmployeeExists() {
        Employee<String> emp = new Employee<>("1", "Kofi Peter", "IT", 6000, 4, 5, true);
        searchManager.getDatabase().addEmployee(emp);
        assertTrue(searchManager.searchByName("Kofi Peter").contains(emp));
    }
}

