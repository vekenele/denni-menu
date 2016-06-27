package cz.muni.fi.pb138.app.services;

import cz.muni.fi.pb138.app.util.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import javax.xml.transform.*;
import java.io.File;

/**
 * Customer Service
 *
 * @author Bc. Jiri Ketner
 */
public class CustomerService {

    private Document doc;
    private File customersXML;
    private static CustomerService instance;

    private CustomerService() {
        customersXML = new File(Path.Web.RESOURCES + "data/customers.xml");
        try {
            doc = XmlService.loadDocument(customersXML);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomerService getInstance() {
        if(instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    /**
     * Finds a customer and returns cid, otherwise returns -1
     * @param name      customer name
     * @param phone     customer phone
     * @return          cid
     */
    private int findCustomer(String name, String phone) {
        NodeList customers = doc.getElementsByTagName("customer");
        if(customers.getLength() > 0) {
            for(int i = 0; i < customers.getLength(); i++) {
                Element customer = (Element)customers.item(i);
                NodeList cname = customer.getElementsByTagName("name");
                NodeList cphone = customer.getElementsByTagName("phone");

                if(name.equals(cname.item(0).getTextContent()) &&
                        phone.equals(cphone.item(0).getTextContent())) {
                    return Integer.parseInt(customer.getAttribute("cid"));
                }
            }
        }
        return -1;
    }

    /**
     * Creates new customer and returns its cid
     * @param name      new customer's name
     * @param phone     new customer's phone
     * @return          new customer's cid
     */
    private int createCustomer(String name, String phone) {
        Element customers = doc.getDocumentElement();
        Element newCustomer = doc.createElement("customer");

        Element customerName = doc.createElement("name");
        customerName.setTextContent(name);

        Element customerPhone = doc.createElement("phone");
        customerPhone.setTextContent(phone);

        newCustomer.appendChild(customerName);
        newCustomer.appendChild(customerPhone);

        NodeList customersList = customers.getElementsByTagName("customer");
        int cid = Integer.parseInt(((Element)customersList.item(customersList.getLength() - 1)).getAttribute("cid"));
        cid += 1;
        newCustomer.setAttribute("cid", Integer.toString(cid));

        customers.appendChild(newCustomer);
        return cid;
    }

    /**
     * If it finds a customer in XML file, then it returns its cid, otherwise
     * creates a new customer's node, appends it to document, save the XML and returns new cid.
     * @param name      customer's name
     * @param phone     customer's phone
     * @return          customer's cid
     */
    public static int returnCustomerId(String name, String phone) {
        CustomerService service = CustomerService.getInstance();
        int cid = service.findCustomer(name, phone);
        if(cid != -1) {
            return cid;
        } else {
            XmlService.removeEmptyTextNodes(service.doc);
            cid = service.createCustomer(name, phone);

            try {
                XmlService.saveChangesToXML(service.doc, service.customersXML);
            } catch(TransformerException e) {
                e.printStackTrace();
            }

            return cid;
        }
    }

    /**
     * If it finds an id in XML file, then it returns its name
     *
     * @param id        customer's id
     * @return          customer's name
     */
    public String getCustomerName(String id) {
        NodeList customers = doc.getElementsByTagName("customer");
        if(customers.getLength() > 0) {
            for(int i = 0; i < customers.getLength(); i++) {
                Element customer = (Element)customers.item(i);
                NodeList cname = customer.getElementsByTagName("name");
                int currentId = Integer.parseInt(customer.getAttribute("cid"));
                int givenId = Integer.parseInt(id);
                if(currentId == givenId) return cname.item(0).getTextContent();
            }
        }
        return "";
    }

    /**
     * If it finds an id in XML file, then it returns its phone
     *
     * @param id        customer's id
     * @return          customer's phone
     */
    public String getCustomerPhone(String id) {
        NodeList customers = doc.getElementsByTagName("customer");
        if(customers.getLength() > 0) {
            for(int i = 0; i < customers.getLength(); i++) {
                Element customer = (Element)customers.item(i);
                NodeList cphone = customer.getElementsByTagName("phone");
                int currentId = Integer.parseInt(customer.getAttribute("cid"));
                int givenId = Integer.parseInt(id);
                if(currentId == givenId) return cphone.item(0).getTextContent();
            }
        }
        return "";
    }
}
