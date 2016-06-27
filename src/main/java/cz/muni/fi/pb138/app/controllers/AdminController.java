package cz.muni.fi.pb138.app.controllers;

import cz.muni.fi.pb138.app.services.MenuService;
import cz.muni.fi.pb138.app.services.XmlService;
import cz.muni.fi.pb138.app.util.Message;
import cz.muni.fi.pb138.app.util.Path;
import cz.muni.fi.pb138.app.util.Utils;
import spark.Filter;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Administration Controller.
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 * @author Peter Neupauer
 */
public class AdminController {

    /**
     * Show the application dashboard.
     */
    public static TemplateViewRoute index = (request, response) -> new ModelAndView(null, "admin/index");

    /**
     * Show the menu.
     */
    public static TemplateViewRoute menu = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        data.put("files", new File(Path.File.XML_STORAGE).listFiles());
        return new ModelAndView(data, "admin/menu");
    };

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
        else if (!Utils.isValidDate(validFrom)) {
            data.put("message", new Message(Message.Level.DANGER, "Field From has invalid date."));
        }
        else if (!Utils.isValidDate(validTo)) {
            data.put("message", new Message(Message.Level.DANGER, "Field To has invalid date."));
        }
        else {
            LocalDate dateFrom = LocalDate.parse(validFrom, Utils.getDateTimeFormatter());
            LocalDate dateTo = LocalDate.parse(validTo, Utils.getDateTimeFormatter());

            XmlService.createEmptyMenuXmlFile(dateFrom, dateTo);

            data.put("message", new Message(Message.Level.SUCCESS, "Menu was successfully created."));
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
    public static Route menuImportPost = (request, response) ->  {
        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }

        Part menuFile = request.raw().getPart("file");
// TODO: 21.06.16 - show result of import to administrator
        if(MenuService.menuFactory(menuFile)) { // parsed successfully and XML created
            System.out.println("Soubor OK");
        } else {
            System.out.println("Soubor NOK"); // parsing or creating failed
        }
        return new ModelAndView(null, "admin/menu-import");
    };

    /**
     * Check if the menu with given ID exists.
     */
    public static Filter menuBeforeFilter = (request, response) -> {
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
    public static TemplateViewRoute menuAddItemPost = (request, response) -> {
        Map<String, Object> data = new HashMap<>();

        System.out.println(request.params(":id"));

        String day = request.queryParams("day").trim();
        String type = request.queryParams("type").trim();
        String name = request.queryParams("name").trim();
        String allergens = request.queryParams("allergens").trim();
        String costPrice = request.queryParams("cost-price").trim();
        String sellPrice = request.queryParams("sell-price").trim();

        data.put("day", day);
        data.put("type", type);
        data.put("name", name);
        data.put("allergens", allergens);
        data.put("costPrice", costPrice);
        data.put("sellPrice", sellPrice);

        // Validation
        if (day.isEmpty() || type.isEmpty() || name.isEmpty() || allergens.isEmpty() || costPrice.isEmpty() || sellPrice.isEmpty()) {
            data.put("message", new Message(Message.Level.DANGER, "All fields are required."));
        }
        else {

            MenuService.getInstance().addItem(request.params(":id") + ".xml", day, type, name, allergens, costPrice, sellPrice);

            data.put("message", new Message(Message.Level.SUCCESS, "Item was added successfully."));
            data.remove("day");
            data.remove("type");
            data.remove("name");
            data.remove("allergens");
            data.remove("costPrice");
            data.remove("sellPrice");
        }

        return new ModelAndView(data, "admin/menu-add-item");
    };

    public static Route menuDelete = (request, response) -> {
        Files.delete(new File(String.format("%s%s.xml", Path.File.XML_STORAGE, request.params("id"))).toPath());
        response.redirect(Path.Admin.MENU);
        return null;
    };

}
