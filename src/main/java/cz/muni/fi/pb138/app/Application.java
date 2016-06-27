package cz.muni.fi.pb138.app;

import cz.muni.fi.pb138.app.controllers.AdminController;
import cz.muni.fi.pb138.app.controllers.MenuController;
import cz.muni.fi.pb138.app.util.Path;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.port;
import static spark.Spark.staticFiles;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;

/**
 * Main Application class.
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 * @author Peter Neupauer
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
        get(Path.Web.INDEX, (req, resp) -> new ModelAndView(null, "index"), engine);

        get(Path.Web.ABOUT, (req, resp) -> new ModelAndView(null, "about"), engine);

        get(Path.Web.CONTACT, (req, resp) -> new ModelAndView(null, "contact"), engine);

        get(Path.Web.PERMANENT_OFFER, (req, resp) -> new ModelAndView(null, "menu/permanent"), engine);

        get(Path.Web.DAILY_MENU, MenuController.menu, engine);
        post(Path.Web.DAILY_MENU, MenuController.preOrder);

        get(Path.Web.DAILY_MENU_PRINT, MenuController.menuPrint, engine);

        // Cron
        get(Path.Cron.ACTUAL_MENU, MenuController.refresh);

        // Admin
        get(Path.Admin.INDEX, AdminController.index);

        get(Path.Admin.MENU, AdminController.menu, engine);

        get(Path.Admin.MENU_CREATE, AdminController.menuCreate, engine);
        post(Path.Admin.MENU_CREATE, AdminController.menuCreatePost, engine);

        get(Path.Admin.MENU_IMPORT, AdminController.menuImport, engine);
        post(Path.Admin.MENU_IMPORT, AdminController.menuImportPost, engine);

        before(Path.Admin.MENU_ADD_ITEM, AdminController.menuBeforeFilter);

        get(Path.Admin.MENU_ADD_ITEM, AdminController.menuAddItem, engine);
        post(Path.Admin.MENU_ADD_ITEM, AdminController.menuAddItemPost, engine);

        before(Path.Admin.MENU_DELETE, AdminController.menuBeforeFilter);
        get(Path.Admin.MENU_DELETE, AdminController.menuDelete);

        get(Path.Admin.PRE_ORDER, AdminController.preOrders, engine);

        get(Path.Admin.PRE_ORDER_CUSTOMERS, AdminController.preOrdersCustomers, engine);

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

