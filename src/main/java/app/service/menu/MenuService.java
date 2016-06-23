package app.service.menu;


import app.model.dailyMenu.DailyMenu;
import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;
import app.model.menu.Menu;
import app.service.xml.XmlService;

import javax.servlet.http.Part;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by david on 20.06.16.
 *
 */
public class MenuService {

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
