package uz.app.clothingstore.exception;

public class ConfirmCodeExpiredException extends RuntimeException {
    public ConfirmCodeExpiredException(String message) {
        super(message);
    }
}