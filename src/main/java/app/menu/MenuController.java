package app.menu;

import app.service.customer.CustomerService;
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
        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuViewXslt.xsl",
                "data/menus/2016-06-27_2016-07-01.xml"));
        return new ModelAndView(data, "menu/dailymenu");
    };

    public static Route preOrder = (request, response) -> {
        String customerName = request.queryParams("customer[name]");
        String customerPhone = request.queryParams("customer[phone]");
        int customerId = CustomerService.returnCustomerId(customerName, customerPhone);

        // iterate over possible foods in order
        for(int i = 0; i < 4; i++) {
            System.out.println(i);
            if(request.queryParams("order[" + i + "][name]") == null) {
                break;
            } else {

                System.out.println(request.queryParams("order[" + i + "][name]"));
            }
        }
        response.status(200);
        response.type("application/json");
        return request.queryParams("order[0][name]");
        //return customerId;
        // make an order in actual menu
        /*
        response.status(200);
        response.type("application/json");
        return 1;
        */
    };

}
