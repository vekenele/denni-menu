package app.model.menu;

import app.model.dailyMenu.DailyMenu;

/**
 * Created by david on 20.06.16.
 *
 * This class represents Menu for the whole week
 */
public class Menu {
    private String validFrom;
    private String validTo;
    private DailyMenu menuMonday;
    private DailyMenu menuTuesday;
    private DailyMenu menuWednesday;
    private DailyMenu menuThursday;
    private DailyMenu menuFriday;

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public DailyMenu getMenuMonday() {
        return menuMonday;
    }

    public void setMenuMonday(DailyMenu menuMonday) {
        this.menuMonday = menuMonday;
    }

    public DailyMenu getMenuTuesday() {
        return menuTuesday;
    }

    public void setMenuTuesday(DailyMenu menuTuesday) {
        this.menuTuesday = menuTuesday;
    }

    public DailyMenu getMenuWednesday() {
        return menuWednesday;
    }

    public void setMenuWednesday(DailyMenu menuWednesday) {
        this.menuWednesday = menuWednesday;
    }

    public DailyMenu getMenuThursday() {
        return menuThursday;
    }

    public void setMenuThursday(DailyMenu menuThursday) {
        this.menuThursday = menuThursday;
    }

    public DailyMenu getMenuFriday() {
        return menuFriday;
    }

    public void setMenuFriday(DailyMenu menuFriday) {
        this.menuFriday = menuFriday;
    }
}
