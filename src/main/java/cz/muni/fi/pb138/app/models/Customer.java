package cz.muni.fi.pb138.app.models;

import cz.muni.fi.pb138.app.services.CustomerService;

/**
 * Created by david on 27.06.16.
 */
public class Customer {
    private String name;
    private String telephone;
    private String id;

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public Customer(String id) {

        this.id = id;
        this.name = CustomerService.getInstance().getCustomerName(id);
        this.telephone = CustomerService.getInstance().getCustomerPhone(id);
    }
}
