package exceptions;

/**
 * Created by niict on 26.12.2017.
 */
public class DependentObjectExistsException extends Exception {
    private final static String MESSAGE = "Object has dependent objects. Try to remove them first";
    public DependentObjectExistsException() {
        this(MESSAGE);
    }

    public DependentObjectExistsException(String message) {
        super(message);
    }

    public DependentObjectExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependentObjectExistsException(Throwable cause) {
        this(MESSAGE, cause);
    }

    public DependentObjectExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
