package cz.muni.fi.pb138.api.v1;

import com.google.gson.Gson;
import cz.muni.fi.pb138.api.v1.common.Error;
import cz.muni.fi.pb138.api.v1.common.HttpStatusCode;
import cz.muni.fi.pb138.api.v1.common.ResponseError;
import cz.muni.fi.pb138.api.v1.common.ResponseStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * The type Restaurant service.
 *
 * @author Peter Neupauer
 */
public class RestaurantService {

    // temporary
    private static final List<TestRestaurant> restaurants = new ArrayList<>();

    /**
     * Sets up routes.
     */
    public static void setUpRoutes(Gson gson) {

        // temporary
        restaurants.add(new TestRestaurant(1L, "Bufet Veveří", "Veveří 158/70", "Brno", "602 00"));
        restaurants.add(new TestRestaurant(2L, "ACADEMIC restaurant", "Netroufalky 770/14", "Brno", "602 00"));
        restaurants.add(new TestRestaurant(3L, "Pizzerie PIZZA NOSTRA", "Kounicova 507/50", "Brno", "602 00"));

        /**
         * GET restaurants
         *
         * Returns a list of all restaurants.
         *
         * Resource Information
         * Response formats     JSON
         *
         * Example Request
         * GET
         * http://domain.com/api/v1/restaurants
         */
        get("/api/v1/restaurants", (request, response) -> restaurants, gson::toJson);

        /**
         * POST restaurants
         *
         * Create a new restaurant.
         *
         * Resource Information
         * Response formats     JSON
         *
         * Parameters
         * name (required)      Restaurant name
         * address (required)   Restaurant address
         * city (required)      Restaurant city
         * zip (required)       Restaurant zip
         *
         * Example Request
         * POST
         * http://domain.com/api/v1/restaurants
         */
        post("/api/v1/restaurants", (request, response) -> {
            if (!request.queryParams().containsAll(Arrays.asList("name", "address", "city", "zip"))) {
                response.status(HttpStatusCode.UNPROCESSABLE_ENTITY);
                return new ResponseError(new Error(1, "Bad data."));
            }
            // temporary // Create new restaurant
            String name = request.queryParams("name");
            String address = request.queryParams("address");
            String city = request.queryParams("city");
            String zip = request.queryParams("zip");
            restaurants.add(new TestRestaurant(4L, name, address, city, zip));

            response.status(HttpStatusCode.CREATED);
            return new ResponseStatus("Created.");
        }, gson::toJson);
    }


}