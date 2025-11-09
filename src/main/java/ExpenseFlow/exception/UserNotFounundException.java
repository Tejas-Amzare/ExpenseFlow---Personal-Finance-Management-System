package ExpenseFlow.exception;

public class UserNotFounundException extends RuntimeException {
    public UserNotFounundException(Long id) {
        super("User not found with ID: " + id);
    }
}
