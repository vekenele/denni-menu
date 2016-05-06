import cz.muni.fi.pb138.web.App;
import org.fluentlenium.adapter.FluentTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import spark.Spark;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {

    private WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @Before
    public void before() {
        String[] args = {};
        App.main(args);
    }

    @After
    public void after() {
        Spark.stop();
    }

    @Test
    public void homeTest() {
        goTo("http://localhost:4567/");
        assertThat(pageSource()).contains("Title");
    }
}