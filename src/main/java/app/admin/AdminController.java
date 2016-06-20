package app.admin;

<<<<<<< HEAD
import app.service.menu.MenuService;
import spark.ModelAndView;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;

=======
import spark.Route;
import spark.ModelAndView;
import spark.TemplateViewRoute;

>>>>>>> 4117d4c5ce7b611194f0c69f45dece8d0e3b0bc0
/**
 * Administration Controller.
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 * @author Peter Neupauer
 * @version 1.0
 */
public class AdminController {

    /**
     * Show the application dashboard.
     */
    public static TemplateViewRoute index = (request, response) -> new ModelAndView(null, "admin/index");

    /**
     * Show the menu.
     */
    public static TemplateViewRoute menu = (request, response) -> new ModelAndView(null, "admin/menu");

    /**
     * Show the menu create form.
     */
    public static TemplateViewRoute menuCreate = (request, response) -> new ModelAndView(null, "admin/menu-create");

    /**
     * Save the menu.
     */
    public static Route menuCreatePost = (request, response) -> "TODO: menu create";

    /**
     * Show the menu import form.
     */
    public static TemplateViewRoute menuImport = (request, response) -> new ModelAndView(null, "admin/menu-import");

<<<<<<< HEAD
        if(MenuService.menuFactory(menuFile)) { // parsed successfully
            System.out.println("Soubor OK");
        } else System.out.println("Soubor NOK"); // parsing failed
=======
    /**
     * Import the menu.
     */
    public static Route menuImportPost = (request, response) -> "TODO: menu import";
>>>>>>> 4117d4c5ce7b611194f0c69f45dece8d0e3b0bc0


//    public static TemplateViewRoute uploadedMenu = (request, response) -> {
//
//
//        HashMap data = new HashMap<>();
//
//        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
//            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
//            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
//        }
//
//        Part menuFile = request.raw().getPart("uploadMenu");
//
//        if(MenuService.saveMenu(menuFile)) { // parsed successfully
//            System.out.println("Soubor OK");
//        } else System.out.println("Soubor NOK"); // parsing failed
//
//
//        return new ModelAndView(null, "admin/uploaded-menu");
//    };


}
