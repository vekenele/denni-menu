package app.util;

/**
 * Created by Jiri Ketner on 17/06/16.
 */
public class Path {

    public static class Web {
        public static final String INDEX = "/";
        public static final String ABOUT = "/about-us";
        public static final String CONTACT = "/contact";
        public static final String DAILYMENU = "/menu/daily";
        public static final String RESOURCES = "src/main/resources/";
        public static final String ANYTHING = "*";
    }

    public static class Admin {
        public static final String INDEX = "/admin";
        public static final String MENU = "/admin/menu";
        public static final String MENU_CREATE = "/admin/menu/create";
        public static final String MENU_IMPORT = "/admin/menu/import";
        public static final String MENU_ADD_ITEM = "/admin/menu/:id/add";
        public static final String XML_STORAGE = "src/main/resources/data/menus/";
        public static final String XSD_PATH = "weekMenu.xsd";

    }
}
