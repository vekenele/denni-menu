package cz.muni.fi.pb138.app.util;

/**
 * Class Path defines a website urls and files location.
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 * @author Peter Neupauer
 */
public class Path {

    /**
     * Web routes.
     */
    public static class Web {
        /**
         * The constant INDEX.
         */
        public static final String INDEX = "/";
        /**
         * The constant ABOUT.
         */
        public static final String ABOUT = "/about-us";
        /**
         * The constant CONTACT.
         */
        public static final String CONTACT = "/contact";
        /**
         * The constant PERMANENT_OFFER.
         */
        public static final String PERMANENT_OFFER = "/menu/permanent";
        /**
         * The constant DAILY_MENU.
         */
        public static final String DAILY_MENU = "/menu/daily";
        /**
         * The constant DAILY_MENU_PRINT.
         */
        public static final String DAILY_MENU_PRINT = "/menu/daily/print";
        /**
         * The constant RESOURCES.
         */
        public static final String RESOURCES = "src/main/resources/";
        /**
         * The constant ANYTHING.
         */
        public static final String ANYTHING = "*";
    }

    /**
     * Admin routes.
     */
    public static class Admin {
        /**
         * The constant INDEX.
         */
        public static final String INDEX = "/admin";
        /**
         * The constant MENU.
         */
        public static final String MENU = "/admin/menu";
        /**
         * The constant MENU_CREATE.
         */
        public static final String MENU_CREATE = "/admin/menu/create";
        /**
         * The constant MENU_IMPORT.
         */
        public static final String MENU_IMPORT = "/admin/menu/import";
        /**
         * The constant MENU_ADD_ITEM.
         */
        public static final String MENU_ADD_ITEM = "/admin/menu/:id/add";
        /**
         * The constant MENU_DELETE.
         */
        public static final String MENU_DELETE = "/admin/menu/:id/delete";
        /**
         * The constant PRE_ORDERS.
         */
        public static final String PRE_ORDER = "/admin/pre-orders";
    }

    /**
     * File paths.
     */
    public static class File {
        /**
         * The constant XML_STORAGE.
         */
        public static final String XML_STORAGE = "src/main/resources/data/menus/";
        /**
         * The constant XSD_PATH.
         */
        public static final String XSD_PATH = "weekMenu.xsd";
    }

    /**
     * Cron routes.
     */
    public static class Cron {
        /**
         * The constant ACTUAL_MENU.
         */
        public static final String ACTUAL_MENU = "/menu/refresh";
    }
}
