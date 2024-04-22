package project.teamproject.exception;

public class NotEnoughTitaniumStockException extends RuntimeException{
    public NotEnoughTitaniumStockException() {
        super();
    }

    public NotEnoughTitaniumStockException(String message) {
        super(message);
    }

    public NotEnoughTitaniumStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughTitaniumStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughTitaniumStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
