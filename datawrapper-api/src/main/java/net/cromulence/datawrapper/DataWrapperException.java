package net.cromulence.datawrapper;

/**
 * Used to indicate exceptions in underlying data stores
 */
public class DataWrapperException extends Exception {
    /** Serial ID */
    private static final long serialVersionUID = 4803733785254678936L;

    public DataWrapperException(String message) {
        super(message);
    }
    
    public DataWrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
