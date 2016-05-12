package cz.muni.fi.pb138.api.v1;

import com.google.gson.Gson;
import cz.muni.fi.pb138.api.v1.common.Error;
import cz.muni.fi.pb138.api.v1.common.HttpStatusCode;
import cz.muni.fi.pb138.api.v1.common.ResponseError;

import static spark.Spark.after;
import static spark.Spark.get;

/**
 * The type Run.
 *
 * @author Peter Neupauer
 */
public class Run {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        Gson gson = new Gson();

        get("/", (request, response) -> "Hello World!");

        /**
         * Set up restaurants service routes.
         */
        RestaurantService.setUpRoutes(gson);

        /**
         * Not found error handling.
         */
        get("*", (request, response) -> {
            response.status(HttpStatusCode.NOT_FOUND);
            return new ResponseError(new Error(0, "Not found – There is no resource behind the URI."));
        }, gson::toJson);

        after((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            response.header("Content-Encoding", "gzip");
        });
    }
}
