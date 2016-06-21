package app.model.dailyMenu;

import app.model.food.Appetizer;
import app.model.food.Dessert;
import app.model.food.MainDish;
import app.model.food.Soup;

import java.util.ArrayList;

/**
 * Created by david on 20.06.16.
 *
 * This class represents Menu for one day
 */
public class DailyMenu {
    private String day;
    private String date;
    private ArrayList<Appetizer> appetizer;
    private ArrayList<Soup> soup;
    private ArrayList<Dessert> dessert;
    private ArrayList<MainDish> dishes;

    public DailyMenu(String day, String date, ArrayList<Appetizer> lAppetizer, ArrayList<Soup> lSoup, ArrayList<Dessert> lDessert, ArrayList<MainDish> lDishes) {
        this.day = day;
        this.date = date;

        dishes = new ArrayList<MainDish>();
        for(MainDish i : lDishes) {
            MainDish lMainDish = new MainDish(i.getName(), i.getAlergens(), i.getCostPrice(), i.getSellPrice());
            dishes.add(lMainDish);
        }

        dessert = new ArrayList<Dessert>();
        for(Dessert i : lDessert) {
            dessert.add(new Dessert(i.getName(), i.getAlergens(), i.getCostPrice(), i.getSellPrice()));
        }

        soup = new ArrayList<Soup>();
        for(Soup i : lSoup) {
            soup.add(new Soup(i.getName(), i.getAlergens(), i.getCostPrice(), i.getSellPrice()));
        }

        appetizer = new ArrayList<Appetizer>();
        for(Appetizer i : lAppetizer) {
            appetizer.add(new Appetizer(i.getName(), i.getAlergens(), i.getCostPrice(), i.getSellPrice()));
        }
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Appetizer> getAppetizer() {
        return appetizer;
    }

    public ArrayList<Soup> getSoup() {
        return soup;
    }

    public ArrayList<Dessert> getDessert() {
        return dessert;
    }

    public ArrayList<MainDish> getDishes() {
        return dishes;
    }
}
