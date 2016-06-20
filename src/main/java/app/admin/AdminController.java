package app.admin;

import app.service.menu.MenuService;
import app.util.Message;
import spark.Filter;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

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
    public static TemplateViewRoute menuCreatePost = (request, response) -> {
        Map<String, Object> data = new HashMap<>();

        String validFrom = request.queryParams("valid-from").trim();
        String validTo = request.queryParams("valid-to").trim();

        data.put("validFrom", validFrom);
        data.put("validTo", validTo);

        // Validation
        if (validFrom.isEmpty()) {
            data.put("message", new Message(Message.Level.DANGER, "Field From is required."));
        }
        else if (validTo.isEmpty()) {
            data.put("message", new Message(Message.Level.DANGER, "Field To is required."));
        }
        else {

            // TODO: Create menu

            data.put("message", new Message(Message.Level.SUCCESS, "Menu was created."));
            data.remove("validFrom");
            data.remove("validTo");
        }

        return new ModelAndView(data, "admin/menu-create");
    };

    /**
     * Show the menu import form.
     */
    public static TemplateViewRoute menuImport = (request, response) -> new ModelAndView(null, "admin/menu-import");
    
    /**
     * Import the menu.
     */
    public static Route menuImportPost = (request, response) -> "TODO: menu import";

    /**
     * Check if the menu with given ID exists.
     */
    public static Filter menuAddItemBeforeFilter = (request, response) -> {
        if (!MenuService.exist(request.params("id"))) {
            response.redirect("/not-found");
        }
    };

    /**
     * Show the menu add item form.
     */
    public static TemplateViewRoute menuAddItem = (request, response) -> new ModelAndView(null, "admin/menu-add-item");

    /**
     * Save the item.
     */
    public static Route menuAddItemPost = (request, response) -> "TODO: menu add item";


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
