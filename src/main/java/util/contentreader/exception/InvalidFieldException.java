package util.contentreader.exception;

public class InvalidFieldException extends  RuntimeException{
    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(final String message ) {
        super( message );
    }

    public InvalidFieldException(final String message, final Throwable cause ) {
        super( message, cause );
    }
}
