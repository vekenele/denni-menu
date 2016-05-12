package cz.muni.fi.pb138.api.v1.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Response error.
 *
 * @author Peter Neupauer
 */
public final class ResponseError {

    /**
     * The Errors.
     */
    List<Error> errors = new ArrayList<>();

    /**
     * Instantiates a new Response error.
     *
     * @param error the error
     */
    public ResponseError(Error error) {
        errors.add(error);
    }

    /**
     * Instantiates a new Response error.
     *
     * @param errors the errors
     */
    public ResponseError(List<Error> errors) {
        this.errors.addAll(errors);
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
