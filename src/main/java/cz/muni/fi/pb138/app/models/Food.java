package cz.muni.fi.pb138.app.models;

/**
 * Created by david on 20.06.16.
 */
public class Food {
    private String name;
    private String alergens;
    private Integer costPrice;
    private Integer sellPrice;

    public Food(String name, String alergens, Integer costPrice, Integer sellPrice) {
        this.name = name;
        this.alergens = alergens;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public String getAlergens() {
        return alergens;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }
}
