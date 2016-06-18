package app.restaurant;

/**
 * Created by Jiri Ketner on 17/06/16.
 */

import spark.*;

public class RestaurantController {
    public static Route getAllRestaurants = (Request request, Response response) -> {
        return RestaurantService.XSLTProcessRestaurants();
    };
}