package cz.muni.fi.pb138.web;

import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import spark.ModelAndView;
import spark.template.pebble.PebbleTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

/**
 * @author Peter Neupauer
 */
public class App {

    public static void main(String[] args) {

        staticFileLocation("public/");

        PebbleTemplateEngine engine = new PebbleTemplateEngine(getLoader());

        /**
         *  Home page.
         */
        get("/", (req, res) -> new ModelAndView(null, "index"), engine);
    }

    private static Loader getLoader() {
        Loader loader = new ClasspathLoader();
        loader.setPrefix("templates/");
        loader.setSuffix(".pebble");
        return loader;
    }
}
