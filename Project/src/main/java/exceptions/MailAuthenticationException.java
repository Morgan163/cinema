package exceptions;

public class MailAuthenticationException extends Exception {
    public MailAuthenticationException() {
    }

    public MailAuthenticationException(String message) {
        super(message);
    }

    public MailAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailAuthenticationException(Throwable cause) {
        super(cause);
    }

    public MailAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
