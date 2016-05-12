package cz.muni.fi.pb138.api.v1.common;

/**
 * The type Response status.
 *
 * @author Peter Neupauer
 */
public final class ResponseStatus {

    private String message;

    /**
     * Instantiates a new Response status.
     *
     * @param message the message
     */
    public ResponseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
