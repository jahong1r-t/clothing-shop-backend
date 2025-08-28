package uz.app.clothingstore.exception;

public class InvalidConfirmCodeException extends RuntimeException {
    public InvalidConfirmCodeException(String message) {
        super(message);
    }
}
