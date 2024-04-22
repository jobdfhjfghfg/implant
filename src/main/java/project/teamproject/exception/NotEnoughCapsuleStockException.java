package project.teamproject.exception;

public class NotEnoughCapsuleStockException extends RuntimeException{
    public NotEnoughCapsuleStockException() {
        super();
    }

    public NotEnoughCapsuleStockException(String message) {
        super(message);
    }

    public NotEnoughCapsuleStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCapsuleStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughCapsuleStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
