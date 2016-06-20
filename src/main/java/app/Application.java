package app;
/**
 * Created by Jiri Ketner on 17/06/16.
 */

import app.admin.AdminController;
import app.restaurant.RestaurantController;
import app.util.Path;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {

        PebbleTemplateEngine engine = new PebbleTemplateEngine(getLoader());

        //-----------------------------
        // Configure Spark
        //-----------------------------

        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        //-----------------------------
        // Application Routes
        //-----------------------------

        // Web
        get(Path.Web.INDEX, RestaurantController.getAllRestaurants, engine);

        // Admin
        get(Path.Admin.INDEX, AdminController.index, engine);

        // Admin/uploaded-menu
        post(Path.Admin.UPLOADED_MENU, AdminController.uploadedMenu, engine);

        // Error
        get(Path.Web.ANYTHING, (req, resp) -> new ModelAndView(null, "404"), engine);

    }

    private static Loader getLoader() {
        Loader loader = new ClasspathLoader();
        loader.setPrefix("templates/");
        loader.setSuffix(".pebble");
        return loader;
    }

}

