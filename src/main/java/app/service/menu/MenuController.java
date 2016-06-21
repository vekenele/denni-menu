package app.service.menu;

import app.service.xslt.XsltTransformService;
import spark.ModelAndView;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiri Ketner on 21/06/16.
 */
public class MenuController {

    public static TemplateViewRoute menu = (request, response) -> {
        Map<String, Object> data = new HashMap<>();
        data.put("menu", XsltTransformService.XSLTProcessFile("xslt/MenuViewXslt.xsl", "data/menus/27_06-01_07-2016.xml"));
        return new ModelAndView(data, "menu/dailymenu");
    };

}
