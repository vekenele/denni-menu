package cz.muni.fi.pb138.api.v1;

import org.junit.rules.ExternalResource;
import spark.Spark;

/**
 * @author Peter Neupauer
 */
public class ServerRule extends ExternalResource {

    protected void before() {
        String[] args = {};
        Run.main(args);
    }

    protected void after() {
        Spark.stop();
    }
}
