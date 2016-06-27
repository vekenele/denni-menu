package cz.muni.fi.pb138.app.models;

import java.util.ArrayList;

/**
 * Created by david on 27.06.16.
 */
public class PreOrders {
    private String name;
    private int count;
    private ArrayList<Customer> customers;

    public PreOrders(String name, int count, ArrayList<Customer> customers) {
        this.name = name;
        this.count = count;
        this.customers = customers;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
