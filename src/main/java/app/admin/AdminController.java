package app.admin;

import spark.ModelAndView;
import spark.TemplateViewRoute;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Neupauer
 */
public class AdminController {

    public static TemplateViewRoute index = (request, response) -> new ModelAndView(null, "admin/index");

    public static TemplateViewRoute uploadedMenu = (request, response) -> {


        HashMap data = new HashMap<>();

        if (request.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }

        Part menuFile = request.raw().getPart("uploadMenu");

        System.out.println("File: " + menuFile.getContentType());
        return new ModelAndView(null, "admin/uploaded-menu");
    };


}
