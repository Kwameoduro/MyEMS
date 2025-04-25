package exceptions;
/**
 * Exception thrown when an employee is not found in the database.
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);  // Exception is properly thrown without logging clutter
    }
}

