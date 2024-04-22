package project.teamproject.exception;

public class NotEnoughCobaltStockException extends RuntimeException{
    public NotEnoughCobaltStockException() {
        super();
    }

    public NotEnoughCobaltStockException(String message) {
        super(message);
    }

    public NotEnoughCobaltStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCobaltStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughCobaltStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
