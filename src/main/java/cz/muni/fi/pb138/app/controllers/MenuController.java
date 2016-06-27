package cz.muni.fi.pb138.app.controllers;

import cz.muni.fi.pb138.app.services.CustomerService;
import cz.muni.fi.pb138.app.services.MenuService;
import cz.muni.fi.pb138.app.services.XsltTransformService;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling menu requests.
 *
 * Created by Jiri Ketner on 21/06/16.
 */
public class MenuController {

    /**
     * This function is responsible for viewing daily menu. It transforms actual menu XML file
     * via XSLT transformation and returns the data in raw, String format.
     */
    public static TemplateViewRoute menu = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        String actualMenu = MenuService.actualMenuFilename();

        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuViewXslt.xsl",
                "data/menus/" + actualMenu));
        return new ModelAndView(data, "menu/dailymenu");
    };

    /**
     * Function menuPrint is responsible for printable version of actual daily menu. It transforms
     * menu XML file via XSLT transformation and returns the data in raw, String format.
     */
    public static TemplateViewRoute menuPrint = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        String actualMenu = MenuService.actualMenuFilename();

        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuPrintXslt.xsl",
                "data/menus/" + actualMenu));
        return new ModelAndView(data, "menu/dailymenu/print");
    };

    /**
     * This function handles request for food preorders. It finds a customer in XML file and
     * returns his id (if doesn't exist, it creates the new one). If the requested order already
     * exists in daily menu XML file, it return 0 (as a fail), otherwise it returns 1 as a success.
     */
    public static Route preOrder = (request, response) -> {
        String customerName = request.queryParams("customer[name]");
        String customerPhone = request.queryParams("customer[phone]");
        int customerId = CustomerService.returnCustomerId(customerName, customerPhone);

        // check, if customer has ordered food yet or something is wrong with request (wrong date)
        if(MenuService.getInstance().hasOrderedBefore(request.queryParams("date"), customerId)) {
            response.status(200);
            response.type("application/json");
            return 0;
        }

        // iterate over possible food types in order
        for(int i = 0; i < 4; i++) {
            if(request.queryParams("order[" + i + "][name]") == null) {
                break;
            } else {
                MenuService.getInstance().preOrderFood(
                    request.queryParams("date"),
                    customerId,
                    request.queryParams("order[" + i + "][name]"),
                    Integer.parseInt(request.queryParams("order[" + i + "][value]")));
            }
        }

        //save Document
        MenuService.getInstance().saveChanges();

        response.status(200);
        response.type("application/json");
        return 1;
    };

    /**
     * This function handles CRON requests for selecting actual daily menu XML file
     * and loading it as a Document object.
     */
    public static Route refresh = (request, response) -> {
        if(MenuService.getInstance().loadActualMenu()) {
            return "OK";
        }
        return "NOK";
    };

}
