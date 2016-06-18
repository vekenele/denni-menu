package app.restaurant;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by Jiri Ketner on 17/06/16.
 */
public class RestaurantService {
    public static String XSLTProcessRestaurants() throws TransformerConfigurationException, TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        URL urlXSL = RestaurantService.class.getClassLoader().getResource("xslt/RestaurantIndexPage.xsl");
        URL urlXML = RestaurantService.class.getClassLoader().getResource("data/restaurants.xml");
        Transformer xsltProc = tf.newTransformer(new StreamSource(new File(urlXSL.getPath())));

        StringWriter outWriter = new StringWriter();
        StreamResult result = new StreamResult(outWriter);

        xsltProc.transform(new StreamSource(new File(urlXML.getPath())), result);

        StringBuffer sb = outWriter.getBuffer();

        return sb.toString(); // TODO
    };
}