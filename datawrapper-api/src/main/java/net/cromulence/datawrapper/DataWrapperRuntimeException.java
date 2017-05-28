package net.cromulence.datawrapper;

/**
 * Used to indicate runtime exceptions in underlying data stores
 */
public class DataWrapperRuntimeException extends RuntimeException {

    /** Serial ID */
    private static final long serialVersionUID = -5619760137410295913L;
    
    public DataWrapperRuntimeException(String message) {
        super(message);
    }
    
    public DataWrapperRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
