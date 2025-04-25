package exceptions;

/**
 * Exception thrown when an invalid salary value is provided.
 */
public class InvalidSalaryException extends RuntimeException {

    public InvalidSalaryException(String message) {
        super(message);  // Keeps exception functionality but removes log clutter
    }
}

