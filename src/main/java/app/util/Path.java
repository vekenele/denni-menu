package app.util;

/**
 * Created by Jiri Ketner on 17/06/16.
 */
public class Path {

    public static class Web {
        public static final String INDEX = "/";
        public static final String ABOUT = "/about-us";
        public static final String CONTACT = "/contact";
        public static final String ANYTHING = "*";
    }

    public static class Admin {
        public static final String INDEX = "/admin";
        public static final String MENU = "/admin/menu";
        public static final String MENU_CREATE = "/admin/menu/create";
        public static final String MENU_IMPORT = "/admin/menu/import";

//        public static final String UPLOADED_MENU = "/admin/uploaded-menu";
    }
}
