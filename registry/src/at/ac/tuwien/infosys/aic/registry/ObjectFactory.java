
package at.ac.tuwien.infosys.aic.registry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.ac.tuwien.infosys.ait09.registry package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Reference_QNAME = new QName("http://www.infosys.tuwien.ac.at/ait09/registry", "Reference");
    private final static QName _Customer_QNAME = new QName("http://www.infosys.tuwien.ac.at/ait09/registry", "Customer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.ac.tuwien.infosys.ait09.registry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RefType }
     * 
     */
    public RefType createRefType() {
        return new RefType();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.infosys.tuwien.ac.at/ait09/registry", name = "Reference")
    public JAXBElement<RefType> createReference(RefType value) {
        return new JAXBElement<RefType>(_Reference_QNAME, RefType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.infosys.tuwien.ac.at/ait09/registry", name = "Customer")
    public JAXBElement<CustomerType> createCustomer(CustomerType value) {
        return new JAXBElement<CustomerType>(_Customer_QNAME, CustomerType.class, null, value);
    }

}
