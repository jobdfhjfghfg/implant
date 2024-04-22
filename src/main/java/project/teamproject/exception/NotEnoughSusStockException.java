package project.teamproject.exception;

public class NotEnoughSusStockException extends RuntimeException{
    public NotEnoughSusStockException() {
        super();
    }

    public NotEnoughSusStockException(String message) {
        super(message);
    }

    public NotEnoughSusStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSusStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughSusStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
