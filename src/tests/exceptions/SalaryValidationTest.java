package tests.exceptions;

import exceptions.InvalidSalaryException;
import model.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalaryValidationTest {

    @Test
    void testNegativeSalaryThrowsException() {
        Exception exception = assertThrows(InvalidSalaryException.class, () -> {
            new Employee<>(1, "Ama Serwah", "IT", -6000, 3, 4, true);  // Fixed salary (negative)
        });
        assertTrue(exception.getMessage().contains("Salary cannot be negative"));
    }

    @Test
    void testZeroSalaryDoesNotThrowException() {
        Employee<Integer> emp = new Employee<>(2, "Ofa Yaw", "HR", 0, 3, 5, true);
        assertEquals(0, emp.getSalary());  // Ensure salary remains valid at zero
    }

    @Test
    void testPositiveSalaryDoesNotThrowException() {
        Employee<Integer> emp = new Employee<>(3, "Kwaku John", "Finance", 7000, 4.5, 6, true);
        assertEquals(7000, emp.getSalary());  // Check positive salary works fine
    }
}
