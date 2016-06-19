package app.restaurant;

/**
 * Created by Jiri Ketner on 17/06/16.
 */

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class RestaurantController {

    public static TemplateViewRoute getAllRestaurants = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        data.put("restaurants", RestaurantService.XSLTProcessRestaurants());
        return new ModelAndView(data, "index");
    };
}