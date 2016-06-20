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
    private Appetizer appetizer;
    private Soup soup;
    private Dessert dessert;
    private ArrayList<MainDish> dishes;

}
