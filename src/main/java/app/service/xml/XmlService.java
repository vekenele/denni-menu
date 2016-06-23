package app.service.xml;

import app.model.dailyMenu.DailyMenu;
import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;
import app.model.menu.Menu;
import app.util.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class providing operations with XML
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 */
public class XmlService {

    /**
     * Take given Menu parameter and create corresponding XML document
     *
     * @param menu
     * @return
     */
    public static boolean createXml (Menu menu) {
        boolean valid = false;

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("dailymenu");
            doc.appendChild(rootElement);

            //valid from and to
            Element validFromElement = doc.createElement("validFrom");
            rootElement.appendChild(validFromElement);
            validFromElement.appendChild(doc.createTextNode(menu.getValidFrom()));

            Element validToElement = doc.createElement("validTo");
            rootElement.appendChild(validToElement);
            validToElement.appendChild(doc.createTextNode(menu.getValidTo()));

            // daily menus
            ArrayList<DailyMenu> menus = menu.getDailyMenus();
            for(DailyMenu m : menus) {

                // day element with date attribute
                Element dateElement = doc.createElement(m.getDay().toLowerCase());
                rootElement.appendChild(dateElement);
                dateElement.setAttribute("date", m.getDate());


                // appetizers
                Element appetizerElement = doc.createElement("appetizer");
                dateElement.appendChild(appetizerElement);
                ArrayList<Appetizer> appetizers = m.getAppetizer();
                for(Appetizer a : appetizers) {
                    Element variantElement = doc.createElement("variant");
                    appetizerElement.appendChild(variantElement);

                    Element nameElement = doc.createElement("name");
                    variantElement.appendChild(nameElement);
                    nameElement.appendChild(doc.createTextNode(a.getName()));

                    Element allergensElement = doc.createElement("allergens");
                    variantElement.appendChild(allergensElement);
                    allergensElement.appendChild(doc.createTextNode(a.getAlergens()));

                    Element costPriceElement= doc.createElement("costPrice");
                    variantElement.appendChild(costPriceElement);
                    costPriceElement.appendChild(doc.createTextNode(a.getCostPrice().toString()));

                    Element sellPriceElement= doc.createElement("sellPrice");
                    variantElement.appendChild(sellPriceElement);
                    sellPriceElement.appendChild(doc.createTextNode(a.getSellPrice().toString()));
                }

                // soups
                Element soupElement = doc.createElement("soup");
                dateElement.appendChild(soupElement);
                ArrayList<Soup> soups = m.getSoup();
                for(Soup s : soups) {
                    Element variantElement = doc.createElement("variant");
                    soupElement.appendChild(variantElement);

                    Element nameElement = doc.createElement("name");
                    variantElement.appendChild(nameElement);
                    nameElement.appendChild(doc.createTextNode(s.getName()));

                    Element allergensElement = doc.createElement("allergens");
                    variantElement.appendChild(allergensElement);
                    allergensElement.appendChild(doc.createTextNode(s.getAlergens()));

                    Element costPriceElement= doc.createElement("costPrice");
                    variantElement.appendChild(costPriceElement);
                    costPriceElement.appendChild(doc.createTextNode(s.getCostPrice().toString()));

                    Element sellPriceElement= doc.createElement("sellPrice");
                    variantElement.appendChild(sellPriceElement);
                    sellPriceElement.appendChild(doc.createTextNode(s.getSellPrice().toString()));
                }

                // main dishes
                Element dishElement = doc.createElement("mainDish");
                dateElement.appendChild(dishElement);
                ArrayList<MainDish> dishes = m.getDishes();
                for(MainDish d : dishes) {
                    Element variantElement = doc.createElement("variant");
                    dishElement.appendChild(variantElement);

                    Element nameElement = doc.createElement("name");
                    variantElement.appendChild(nameElement);
                    nameElement.appendChild(doc.createTextNode(d.getName()));

                    Element allergensElement = doc.createElement("allergens");
                    variantElement.appendChild(allergensElement);
                    allergensElement.appendChild(doc.createTextNode(d.getAlergens()));

                    Element costPriceElement= doc.createElement("costPrice");
                    variantElement.appendChild(costPriceElement);
                    costPriceElement.appendChild(doc.createTextNode(d.getCostPrice().toString()));

                    Element sellPriceElement= doc.createElement("sellPrice");
                    variantElement.appendChild(sellPriceElement);
                    sellPriceElement.appendChild(doc.createTextNode(d.getSellPrice().toString()));
                }

                // deserts
                Element dessertElement = doc.createElement("dessert");
                dateElement.appendChild(dessertElement);
                ArrayList<Dessert> desserts = m.getDessert();
                for(Dessert t : desserts) {
                    Element variantElement = doc.createElement("variant");
                    dessertElement.appendChild(variantElement);

                    Element nameElement = doc.createElement("name");
                    variantElement.appendChild(nameElement);
                    nameElement.appendChild(doc.createTextNode(t.getName()));

                    Element allergensElement = doc.createElement("allergens");
                    variantElement.appendChild(allergensElement);
                    allergensElement.appendChild(doc.createTextNode(t.getAlergens()));

                    Element costPriceElement= doc.createElement("costPrice");
                    variantElement.appendChild(costPriceElement);
                    costPriceElement.appendChild(doc.createTextNode(t.getCostPrice().toString()));

                    Element sellPriceElement= doc.createElement("sellPrice");
                    variantElement.appendChild(sellPriceElement);
                    sellPriceElement.appendChild(doc.createTextNode(t.getSellPrice().toString()));
                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File( Path.Admin.XML_STORAGE + menu.getValidFrom() + "_" + menu.getValidTo() + ".xml"));



            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            valid = validateXml(Path.Admin.XML_STORAGE + menu.getValidFrom() + "_" + menu.getValidTo() + ".xml");
            System.out.println("File saved!");


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return valid;
    }

    /**
     * Validate XML against XML Schema
     *
     *@param xmlPath
     * @return
     */
    public static boolean validateXml (String xmlPath) {

        Source xsdFile = new StreamSource(new File(Path.Admin.XSD_PATH));
        Source xmlFile = new StreamSource(new File(xmlPath));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(xsdFile);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Validator validator = schema.newValidator();
        try {
            try {
                validator.validate(xmlFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid");
            System.out.println("Reason: " + e.getLocalizedMessage());
            return false;
        }


        return true;
    }

    /**
     * Loads an XML file given in input parameter
     * @param file      Input XML file
     * @return          Document document
     */
    public static Document loadDocument(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(file);
    }

    /**
     * This function removes empty text nodes from document. Then transformer indenting
     * works well.
     * Source: http://stackoverflow.com/questions/978810/how-to-strip-whitespace-only-text-nodes-from-a-dom-before-serialization
     */
    public static void removeEmptyTextNodes(Document doc) {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        // XPath to find empty text nodes.
        NodeList emptyTextNodes = null;
        try {
            XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");
            emptyTextNodes = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);
        } catch(XPathExpressionException e) {
            e.printStackTrace();
        }

        // Remove each empty text node from document.
        if(emptyTextNodes != null) {
            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            }
        }
    }

    /**
     * Saves changes to source XML. This function is used when new customer is appended to document
     * or new preorder is requested.
     */
    public static void saveChangesToXML(Document doc, File xml) throws TransformerException {
        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xml);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(source, result);
    }
}
