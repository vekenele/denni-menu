package app.menu;

import app.service.customer.CustomerService;
import app.service.menu.MenuService;
import app.service.xslt.XsltTransformService;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiri Ketner on 21/06/16.
 */
public class MenuController {

    public static TemplateViewRoute menu = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        String actualMenu = MenuService.actualMenuFilename();

        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuViewXslt.xsl",
                "data/menus/" + actualMenu));
        return new ModelAndView(data, "menu/dailymenu");
    };

    public static TemplateViewRoute menuPrint = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        String actualMenu = MenuService.actualMenuFilename();

        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuPrintXslt.xsl",
                "data/menus/" + actualMenu));
        return new ModelAndView(data, "menu/dailymenu/print");
    };

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

    public static Route refresh = (request, response) -> {
        if(MenuService.getInstance().loadActualMenu()) {
            return "OK";
        }
        return "NOK";
    };

}
