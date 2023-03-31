package uz.pdp.agrarmarket.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
    super(userNotFound);
    }
}
