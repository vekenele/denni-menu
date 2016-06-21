package app.service.xml;

import app.model.dailyMenu.DailyMenu;
import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;
import app.model.menu.Menu;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by david on 21.06.16.
 *
 * Class providing operations with XML
 */
public class XmlService {

    /**
     * Take given Menu parameter and create corresponding XML document
     *
     * @param menu
     * @return
     */
    public static boolean createXml (Menu menu) {

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
            StreamResult result = new StreamResult(new File("src/main/resources/data/menus/" + menu.getValidFrom() + "_" + menu.getValidTo() + ".xml"));


            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }

            System.out.println("File saved!");


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return true;
    }
}
