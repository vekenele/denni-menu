package app;
/**
 * Created by Jiri Ketner on 17/06/16.
 */

import app.restaurant.*;
import app.util.*;
import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        // Configure Spark
        port(4567);
        //staticFiles.location("/public");
        //staticFiles.expireTime(600L);

        // Set up routes
        get(Path.Web.INDEX,          RestaurantController.getAllRestaurants);
        //get("*",                   something.notFound); 404 page TODO

    }

}

