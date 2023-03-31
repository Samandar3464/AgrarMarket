package uz.pdp.agrarmarket.exception;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String usernameAlreadyExist) {
    super(usernameAlreadyExist);
    }
}
