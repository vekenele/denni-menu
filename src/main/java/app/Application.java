package app;

import app.admin.AdminController;
import app.restaurant.RestaurantController;
import app.util.Path;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.*;

/**
 * Main Application class.
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 * @author Peter Neupauer
 * @version 1.0
 */
public class Application {

    /**
     * Here are all the registered application routes.
     *
     * @param args command line arguments
     */
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
        get(Path.Admin.MENU, AdminController.menu, engine);
        get(Path.Admin.MENU_CREATE, AdminController.menuCreate, engine);
        post(Path.Admin.MENU_CREATE, AdminController.menuCreatePost);
        get(Path.Admin.MENU_IMPORT, AdminController.menuImport, engine);
        post(Path.Admin.MENU_IMPORT, AdminController.menuImportPost);

//        // Admin/uploaded-menu
//        post(Path.Admin.UPLOADED_MENU, AdminController.uploadedMenu, engine);

        // Error
        get(Path.Web.ANYTHING, (req, resp) -> new ModelAndView(null, "404"), engine);

    }

    /**
     * Creates new instance of ClasspathLoader.
     *
     * @return class path loader
     */
    private static Loader getLoader() {
        Loader loader = new ClasspathLoader();
        loader.setPrefix("templates/");
        loader.setSuffix(".pebble");
        return loader;
    }

}

