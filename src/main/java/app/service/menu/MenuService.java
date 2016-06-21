package app.service.menu;


import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

        String menuDay;
        String menuDate;
        String[] tmpBuffer;
        Appetizer appetizer;
        Soup soup;
        Dessert dessert;
        ArrayList<MainDish> dishes = null;

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
                System.out.println("Line: " + line);

                // if day is in the current line
                if(line.toLowerCase().matches("^\\*.+\\*$")) {
                    tmpBuffer = line.replace("*", "").split(";");
                    menuDay = tmpBuffer[0];
                    menuDate = tmpBuffer[1];
                } else if(line.toLowerCase().matches("^\\[appetizer\\].+\\[\\/appetizer\\]$")) {            // if appetizer is in the current line
                    tmpBuffer = line.replace("[appetizer]", "").replace("[/appetizer]", "").split(";");
                    appetizer = new Appetizer(tmpBuffer[0], tmpBuffer[1], Integer.parseInt(tmpBuffer[2]), Integer.parseInt(tmpBuffer[3]));
                } else if(line.toLowerCase().matches("^\\[soup\\].+\\[\\/soup\\]$")) {                      // if soup is in the current line
                    tmpBuffer = line.replace("[soup]", "").replace("[/soup]", "").split(";");
                    soup = new Soup(tmpBuffer[0], tmpBuffer[1], Integer.parseInt(tmpBuffer[2]), Integer.parseInt(tmpBuffer[3]));
                } else if(line.toLowerCase().matches("^\\[desert\\].+\\[\\/desert\\]$")) {                  // if dessert is in the current line
                    tmpBuffer = line.replace("[desert]", "").replace("[/desert]", "").split(";");
                    dessert = new Dessert(tmpBuffer[0], tmpBuffer[1], Integer.parseInt(tmpBuffer[2]), Integer.parseInt(tmpBuffer[3]));
                } else if(line.toLowerCase().matches("^\\[maindish\\].+\\[\\/maindish\\]$")) {                  // if mainDish is in the current line
                    tmpBuffer = line.toLowerCase().replace("[maindish]", "").replace("[/maindish]", "").split("\\*");
                    for(int i = 0; i < tmpBuffer.length; i++ ) {
                        String[] tmpArray = tmpBuffer[i].split(";");
                        System.out.println("0: " + tmpArray[0] + ", 1: " + tmpArray[1] + ", 2: " + tmpArray[2] + ", 3: " + tmpArray[3]);
                       // dishes.add(new MainDish(tmpArray[0], tmpArray[1], Integer.parseInt(tmpArray[2]), Integer.parseInt(tmpArray[3])));

                    }
                   // System.out.println("List: " + Arrays.toString(dishes.toArray()));
                }


            }
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

    public static boolean exist(String name) throws IOException {
        return Files.walk(Paths.get("src/main/resources/data/menus")).anyMatch(path -> path.getFileName().toString().equals(name + ".xml"));
    }
}
