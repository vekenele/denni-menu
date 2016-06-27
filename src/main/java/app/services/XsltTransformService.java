package app.services;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;
import java.net.URL;

/**
 * Class for performing XSLT transformations
 *
 * Created by ketys on 21/06/16.
 */
public class XsltTransformService {

    /**
     * This method transforms XML file via XSLT transformation and returns result as a String
     * @param xslFile   processing XSLT file
     * @param xmlFile   input XML file
     * @return          String of transformed input XML file
     */
    public static String XSLTProcessFile(String xslFile, String xmlFile) throws TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        URL urlXSL = XsltTransformService.class.getClassLoader().getResource(xslFile);
        URL urlXML = XsltTransformService.class.getClassLoader().getResource(xmlFile);

        String xslPath = "";
        String xmlPath = "";
        try {
            xslPath = urlXSL.getPath();
            xmlPath = urlXML.getPath();
        } catch(NullPointerException e) {
            //XML does not exists, no daily menu is prepared
            return "<div class='alert alert-danger' role='alert'><p class='text-center'>Currently isn't available any daily menu. Please come back later.</p></div>";
        }

        Transformer xsltProc = tf.newTransformer(new StreamSource(new File(xslPath)));

        StringWriter outWriter = new StringWriter();
        StreamResult result = new StreamResult(outWriter);

        xsltProc.transform(new StreamSource(new File(xmlPath)), result);

        StringBuffer sb = outWriter.getBuffer();

        return sb.toString();
    }

}
