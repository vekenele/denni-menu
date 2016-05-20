package cz.muni.fi.pb138;

import static spark.Spark.get;

/**
 * @author Peter Neupauer
 */
public class App {

    public static void main(String[] args) {
        get("/", (request, response) -> "Hello Worlde!");
    }
}
