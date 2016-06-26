package app.service.menu;


import app.model.dailyMenu.DailyMenu;
import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;
import app.model.menu.Menu;
import app.service.xml.XmlService;
import app.util.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.Part;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * MenuService
 * Singleton
 *
 * @author Bc. Jiří Ketner
 * @author Bc. David Věžník
 */
public class MenuService {

    private Document doc;
    private File actualMenu;
    private static MenuService instance;

    private MenuService() {
        loadActualMenu();
    }

    public static MenuService getInstance() {
        if(instance == null) {
            instance = new MenuService();
        }
        return instance;
    }

    /**
     * Inits actual class attributes (doc, actualMenu). It's used, when the class is instantiated
     * or when we access to a specific route for the actual menu change (every Sunday). We can set a CRON
     * in the future...
     *
     * @return          true, if everything is OK
     */
    public boolean loadActualMenu() {
        String actualFilename = actualMenuFilename();
        actualMenu = new File(Path.Web.RESOURCES + "data/menus/" + actualFilename);
        if(actualMenu.exists() && !actualMenu.isDirectory()) {
            try {
                doc = XmlService.loadDocument(actualMenu);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches and returns NodeList based on input XPath expression
     *
     * @param xpathExpr     input XPath expression
     * @return              NodeList corresponding to given XPath expression
     */
    private NodeList getNodeListByXPath(String xpathExpr) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        NodeList result = null;
        try {
            expr = xpath.compile(xpathExpr);
            result = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
        } catch(XPathExpressionException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * The function looks for the preorder on the specified day (via dayDate) of specified customer
     * (via customerId) ... if preorder isset, then returns true.
     * @param dayDate       specified day
     * @param customerId    specified customer
     * @return              true, if preorder exists | day with specified dayDate doesn't exist (wrong request)
     */
    public boolean hasOrderedBefore(String dayDate, int customerId) {
        NodeList specifiedDays = getNodeListByXPath("/dailymenu/*[@date='" + dayDate + "']");
        if(specifiedDays.getLength() == 0) {
            return true;
        } else {
            Element day = (Element)specifiedDays.item(0);
            NodeList customers = day.getElementsByTagName("customer");
            if(customers.getLength() > 0) {
                for(int i = 0; i < customers.getLength(); i++) {
                    Element customer = (Element)customers.item(i);
                    if(customerId == Integer.parseInt(customer.getAttribute("cid"))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Create element customer element inside parent element specified
     * @param parent        parent Element
     * @param customerId    customer id
     */
    private void createElementCustomer(Element parent, int customerId) {
        Element newCustomer = doc.createElement("customer");
        newCustomer.setAttribute("cid", Integer.toString(customerId));
        parent.appendChild(newCustomer);
    }

    /**
     * Create element preorders inside parent Element, with customer inside it
     * @param parent
     * @param customerId
     */
    private void createElementPreordersWithCustomer(Element parent, int customerId) {
        Element newPreorders = doc.createElement("preorders");
        createElementCustomer(newPreorders, customerId);
        parent.appendChild(newPreorders);
    }

    /**
     * Method for preordering concrete food on concrete day.
     * @param dayDate           date on which order should be placed
     * @param customerId        customer, which is ordering something
     * @param foodType          type of food (desert, appetizer etc.)
     * @param foodVariant       number of food variant
     * @return                  true, if order was processed, false otherwise
     */
    public void preOrderFood(String dayDate, int customerId, String foodType, int foodVariant) {
        NodeList concreteFoodTypeList = getNodeListByXPath("/dailymenu/*[@date='" + dayDate + "']/" + foodType);
        Element concreteFoodType = (Element)concreteFoodTypeList.item(0);
        NodeList variants = concreteFoodType.getElementsByTagName("variant");
        if(variants.getLength() > 0) {
            Element chosenVariant = (Element)variants.item(foodVariant-1);
            NodeList preorders = chosenVariant.getElementsByTagName("preorders");
            if(preorders.getLength() > 0) {
                Element preorderEl = (Element)preorders.item(0);
                createElementCustomer(preorderEl, customerId);
            } else {
                createElementPreordersWithCustomer(chosenVariant, customerId);
            }
        } else {
            NodeList preorders = concreteFoodType.getElementsByTagName("preorders");
            if(preorders.getLength() > 0) {
                Element preorderEl = (Element)preorders.item(0);
                createElementCustomer(preorderEl, customerId);
            } else {
                createElementPreordersWithCustomer(concreteFoodType, customerId);
            }
        }
    }

    /**
     * Save changes to source XML
     */
    public void saveChanges() {
        XmlService.removeEmptyTextNodes(doc);
        try {
            XmlService.saveChangesToXML(doc, actualMenu);
        } catch(TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines current week and based on it - generates String of filename, which stores data for actual week.
     * It "switches" to next week always on Sunday.
     * @return      String filename for actual XML
     */
    public static String actualMenuFilename() {
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int dayFrom = calendar.get(Calendar.DAY_OF_MONTH);
        int monthFrom = calendar.get(Calendar.MONTH) + 1;
        int yearFrom = calendar.get(Calendar.YEAR);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        int dayTo = calendar.get(Calendar.DAY_OF_MONTH);
        int monthTo = calendar.get(Calendar.MONTH) + 1;
        int yearTo = calendar.get(Calendar.YEAR);

        String result = yearFrom + "-";
        result += (monthFrom < 10) ? "0" + monthFrom + "-" : monthFrom + "-";
        result += (dayFrom < 10) ? "0" + dayFrom : dayFrom;
        result += "_" + yearTo + "-";
        result += (monthTo < 10) ? "0" + monthTo + "-" : monthTo + "-";
        result += (dayTo < 10) ? "0" + dayTo + ".xml" : dayTo + ".xml";

        return result;
    }

    /*
     * This method parses input file and creates all objects required for Menu.
     * If everything is OK (parsing, objects creating) method returns true, else false
     */
    public static boolean menuFactory(final Part uploadedMenu) {

        String menuDay = null;
        String menuDate = null;
        String validFrom = null;
        String validTo = null;
        String[] tmpBuffer;
        ArrayList<MainDish> dishes = new ArrayList<MainDish>();
        ArrayList<Appetizer> appetizers = new ArrayList<Appetizer>();
        ArrayList<Soup> soups = new ArrayList<Soup>();
        ArrayList<Dessert> desserts = new ArrayList<Dessert>();
        ArrayList<DailyMenu> dailyMenus = new ArrayList<DailyMenu>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(uploadedMenu.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                // if day is in the current line
                if(line.toLowerCase().matches("^\\*.+\\*$")) {
                    tmpBuffer = line.replace("*", "").split(";");
                    menuDay = tmpBuffer[0];
                    menuDate = tmpBuffer[1];
                    if(menuDay.toLowerCase().equals("monday")) {
                        validFrom = menuDate;
                    }
                    if(menuDay.toLowerCase().equals("friday")) {
                        validTo = menuDate;
                    }
                } else if(line.toLowerCase().matches("^\\[appetizer\\].+\\[\\/appetizer\\]$")) {            // if appetizer is in the current line
                    tmpBuffer = line.replace("[appetizer]", "").replace("[/appetizer]", "").split("\\*");
                    appetizers.clear();
                    for(int i = 0; i < tmpBuffer.length; i++ ) {
                        String[] tmpArray = tmpBuffer[i].split(";");
                        Appetizer appetizer = new Appetizer(tmpArray[0], tmpArray[1], Integer.parseInt(tmpArray[2]), Integer.parseInt(tmpArray[3]));
                        appetizers.add(appetizer);
                    }
                } else if(line.toLowerCase().matches("^\\[soup\\].+\\[\\/soup\\]$")) {                      // if soup is in the current line
                    tmpBuffer = line.replace("[soup]", "").replace("[/soup]", "").split("\\*");
                    soups.clear();
                    for(int i = 0; i < tmpBuffer.length; i++ ) {
                        String[] tmpArray = tmpBuffer[i].split(";");
                        Soup soup = new Soup(tmpArray[0], tmpArray[1], Integer.parseInt(tmpArray[2]), Integer.parseInt(tmpArray[3]));
                        soups.add(soup);
                    }
                } else if(line.toLowerCase().matches("^\\[dessert\\].+\\[\\/dessert\\]$")) {                  // if dessert is in the current line
                    tmpBuffer = line.replace("[dessert]", "").replace("[/dessert]", "").split("\\*");
                    desserts.clear();
                    for(int i = 0; i < tmpBuffer.length; i++ ) {
                        String[] tmpArray = tmpBuffer[i].split(";");
                        Dessert dessert = new Dessert(tmpArray[0], tmpArray[1], Integer.parseInt(tmpArray[2]), Integer.parseInt(tmpArray[3]));
                        desserts.add(dessert);
                    }
                } else if(line.toLowerCase().matches("^\\[maindish\\].+\\[\\/maindish\\]$")) {                  // if mainDish is in the current line
                    tmpBuffer = line.replace("[mainDish]", "").replace("[/mainDish]", "").split("\\*");
                    dishes.clear();
                    for(int i = 0; i < tmpBuffer.length; i++ ) {
                        String[] tmpArray = tmpBuffer[i].split(";");
                        MainDish mainDish = new MainDish(tmpArray[0], tmpArray[1], Integer.parseInt(tmpArray[2]), Integer.parseInt(tmpArray[3]));
                        dishes.add(mainDish);
                    }
                } else if(line.toLowerCase().matches("^-+$")) {                  // if there is a day separator
                    // this should be a valid menu
                    if(menuDay.length() > 0 && menuDate.length() > 0 && soups.size() > 0 && dishes.size() > 0) {
                        DailyMenu dailyMenu = new DailyMenu(menuDay, menuDate, appetizers, soups, desserts, dishes);
                        dailyMenus.add(dailyMenu);
                    } else return false;
                }
            }
            // if all 5 days is properly parsed create final Menu object
            if(dailyMenus.size() == 5) {
                Menu menu = new Menu(validFrom, validTo, dailyMenus);
                printMenu(menu);
                boolean menuCreated = createXml(menu);
                if(menuCreated) {
                    System.out.println("Menu created");
                } else {
                    System.out.println("Menu was not create");
                    return false;
                }

            } else return false;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static boolean createXml(Menu menu) {
        return XmlService.createXml(menu);
    }

    public static boolean exist(String name) throws IOException {
        return Files.walk(Paths.get("src/main/resources/data/menus")).anyMatch(path -> path.getFileName().toString().equals(name + ".xml"));
    }



    /*
     * Print given Menu object in readable form
     */
    public static void printMenu (Menu menu) {
        System.out.println("Valid from " + menu.getValidFrom());
        System.out.println("Valid to " + menu.getValidTo());
        ArrayList<DailyMenu> dailyMenus = menu.getDailyMenus();

        for(DailyMenu i : dailyMenus) {

            ArrayList<Appetizer> appetizers = i.getAppetizer();
            for(Appetizer j: appetizers) {
                System.out.println("Appetizer name: " + j.getName());
                System.out.println("Appetizer alergens: " + j.getAlergens());
                System.out.println("Appetizer cost price: " + j.getCostPrice());
                System.out.println("Appetizer sell price: " + j.getSellPrice());
            }

            ArrayList<Soup> soups = i.getSoup();
            for(Soup j: soups) {
                System.out.println("Soup name: " + j.getName());
                System.out.println("Soup alergens: " + j.getAlergens());
                System.out.println("Soup cost price: " + j.getCostPrice());
                System.out.println("Soup sell price: " + j.getSellPrice());
            }

            ArrayList<Dessert> desserts = i.getDessert();
            for(Dessert j: desserts) {
                System.out.println("Dessert name: " + j.getName());
                System.out.println("Dessert alergens: " + j.getAlergens());
                System.out.println("Dessert cost price: " + j.getCostPrice());
                System.out.println("Dessert sell price: " + j.getSellPrice());
            }

            ArrayList<MainDish> mainDishes = i.getDishes();
            for(MainDish j: mainDishes) {
                System.out.println("Dish name: " + j.getName());
                System.out.println("Dish alergens: " + j.getAlergens());
                System.out.println("Dish cost price: " + j.getCostPrice());
                System.out.println("Dish sell price: " + j.getSellPrice());
            }

            System.out.println("-----------------------");

        }
    }
}
