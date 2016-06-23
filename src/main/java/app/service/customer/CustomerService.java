package app.service.customer;

import app.util.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.net.URL;

/**
 * Created by Jiri Ketner on 22/06/16.
 *
 * Singleton
 */
public class CustomerService {

    private Document doc;
    private File customersXML;
    private static CustomerService instance;

    private CustomerService() {
        customersXML = new File(Path.Web.RESOURCES + "data/customers.xml");
        try {
            loadDocument(customersXML);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDocument(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        doc = factory.newDocumentBuilder().parse(file);
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
     * This function removes empty text nodes from document. Then transformer indenting
     * works well.
     * Source: http://stackoverflow.com/questions/978810/how-to-strip-whitespace-only-text-nodes-from-a-dom-before-serialization
     */
    private void removeEmptyTextNodes() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        // XPath to find empty text nodes.
        NodeList emptyTextNodes = null;
        try {
            XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");
            emptyTextNodes = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);
        } catch(XPathExpressionException e) {
            e.printStackTrace();
        }

        // Remove each empty text node from document.
        if(emptyTextNodes != null) {
            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            }
        }
    }

    /**
     * Saves changes to source XML
     */
    private void saveChangesToXML() throws TransformerException {
        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(customersXML);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(source, result);
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
            service.removeEmptyTextNodes();
            cid = service.createCustomer(name, phone);

            try {
                service.saveChangesToXML();
            } catch(TransformerException e) {
                e.printStackTrace();
            }

            return cid;
        }
    }
}
