package cz.muni.fi.pb138.api.v1;

/**
 * @author Peter Neupauer
 */
public class TestRestaurant {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String zip;

    public TestRestaurant(Long id, String name, String address, String city, String zip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
