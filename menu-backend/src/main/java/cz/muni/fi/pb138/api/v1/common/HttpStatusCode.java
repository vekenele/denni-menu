package cz.muni.fi.pb138.api.v1.common;

/**
 * The type Http status code.
 *
 * @author Peter Neupauer
 */
public final class HttpStatusCode {

    /**
     * Success!
     */
    public static final int OK = 200;
    /**
     * New resource has been created.
     */
    public static final int CREATED = 201;
    /**
     * The resource was successfully deleted.
     */
    public static final int DELETED = 204;
    /**
     * There was no new data to return.
     */
    public static final int NOT_MODIFIED = 304;
    /**
     * The request was invalid or cannot be otherwise served.
     */
    public static final int BAD_REQUEST = 400;
    /**
     * The request requires an user authentication.
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * The server understood the request, but is refusing it or the access is not allowed.
     */
    public static final int FORBIDDEN = 403;
    /**
     * There is no resource behind the URI.
     */
    public static final int NOT_FOUND = 404;
    /**
     * Should be used if the server cannot process the entity,
     * e.g. if an image cannot be formatted or mandatory fields are missing in the payload.
     */
    public static final int UNPROCESSABLE_ENTITY = 422;
    /**
     * Something is broken.
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

}
