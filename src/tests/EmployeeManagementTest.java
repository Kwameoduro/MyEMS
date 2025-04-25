package tests;

import database.EmployeeDatabase;
import model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagementTest {
    private final EmployeeDatabase<String> database = new EmployeeDatabase<>();

    @Test
    void testAddEmployee() {
        Employee<String> employee = new Employee<>("001", "Kofi Peter", "HR", 5000, 4.8, 5, true);
        database.addEmployee(employee);

        assertEquals(employee, database.getEmployeeByID("001"));
    }

    @Test
    void testSearchByDepartment() {
        Employee<String> employee1 = new Employee<>("002", "Yaa Boakye", "IT", 6000, 4.5, 3, true);
        Employee<String> employee2 = new Employee<>("003", "Ama Mansa", "IT", 5500, 3.8, 4, true);
        database.addEmployee(employee1);
        database.addEmployee(employee2);

        assertEquals(2, database.getAllEmployees().values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase("IT")).count());
    }

    @Test
    void testRemoveEmployee() {
        Employee<String> employee = new Employee<>("004", "Ofa KooPoku", "Finance", 7000, 4.9, 6, true);
        database.addEmployee(employee);

        database.removeEmployee("004");
        assertThrows(Exception.class, () -> database.getEmployeeByID("004"));
    }
}
