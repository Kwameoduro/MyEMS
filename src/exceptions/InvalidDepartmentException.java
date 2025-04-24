package exceptions;

/**
 * Exception thrown when an invalid department name is entered.
 */
public class InvalidDepartmentException extends RuntimeException {

    public InvalidDepartmentException(String message) {
        super(message);  // Keeps exception functionality but removes unnecessary logs
    }
}
