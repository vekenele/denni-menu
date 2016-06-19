package app.admin;

import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 * @author Peter Neupauer
 */
public class AdminController {

    public static TemplateViewRoute index = (request, response) -> new ModelAndView(null, "admin/index");
}
