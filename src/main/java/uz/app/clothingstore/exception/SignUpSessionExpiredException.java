package uz.app.clothingstore.exception;

public class SignUpSessionExpiredException extends RuntimeException {
    public SignUpSessionExpiredException(String message) {
        super(message);
    }
}

