package project.teamproject.exception;

public class NotEnoughBoxStockException extends RuntimeException{
    public NotEnoughBoxStockException() {
        super();
    }

    public NotEnoughBoxStockException(String message) {
        super(message);
    }

    public NotEnoughBoxStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughBoxStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughBoxStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
