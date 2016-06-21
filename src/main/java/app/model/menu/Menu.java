package app.model.menu;

import app.model.dailyMenu.DailyMenu;

import java.util.ArrayList;

/**
 * Created by david on 20.06.16.
 *
 * This class represents Menu for a whole week
 */
public class Menu {
    private String validFrom;
    private String validTo;
    private ArrayList<DailyMenu> dailyMenus;


    public Menu(String validFrom, String validTo, ArrayList<DailyMenu> dailyMenus) {
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.dailyMenus = dailyMenus;
    }

    public String getValidFrom() {
        return validFrom;
    }


    public String getValidTo() {
        return validTo;
    }

    public ArrayList<DailyMenu> getDailyMenus() {
        return dailyMenus;
    }
}
