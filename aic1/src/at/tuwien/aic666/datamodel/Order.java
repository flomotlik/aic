package at.tuwien.aic666.datamodel;

import at.tuwien.aic666.util.DateLongAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="order")
public class Order {

    @XmlAttribute(name="id")
    private String id;

    @XmlJavaTypeAdapter(DateLongAdapter.class)
    private Date orderDate;

    private Customer customer;

    @XmlElementWrapper(name = "items")
    @XmlElements(@XmlElement(name = "item"))
    private Collection<Item> items;

    public Order() {
        this.items = new ArrayList<Item>();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Collection<Item> getItems() {
        return this.items;
    }

    public void setItems(final Collection<Item> items) {
        this.items = items;
    }
}
