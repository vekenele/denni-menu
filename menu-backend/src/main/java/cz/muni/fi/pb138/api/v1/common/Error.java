package cz.muni.fi.pb138.api.v1.common;

/**
 * The type Error.
 *
 * @author Peter Neupauer
 */
public class Error {

    private int code;

    private String message;

    /**
     * Instantiates a new Error.
     *
     * @param code    the code
     * @param message the message
     */
    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
