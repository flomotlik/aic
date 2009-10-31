package at.tuwien.aic666.persistence;

import at.tuwien.aic666.datamodel.Address;
import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author peter
 */
public class DataBaseMock {

    private Collection<Customer> customers;
    private Collection<Item> items;
    private Map<Item, Integer> availableItemsList;
    private Map<Item, Address> shippingList;
    private Map<Item, Address> shippedList;
    
    private static DataBaseMock uniqueInstance;


    //TODO do we need to make the methods ThreadSafe...
    public static synchronized DataBaseMock getInstance() {
        if (DataBaseMock.uniqueInstance == null) {
            DataBaseMock.uniqueInstance = new DataBaseMock();
        }

        return DataBaseMock.uniqueInstance;
    }

    private DataBaseMock() {
        this.customers = new ArrayList<Customer>();
        this.items = new ArrayList<Item>();
        this.availableItemsList = new HashMap<Item, Integer>();
        this.shippedList = new HashMap<Item, Address>();
        this.shippingList = new HashMap<Item, Address>();

    }

    // -- getter and setter --
    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Map<Item, Integer> getAvailableItemsList() {
        return availableItemsList;
    }

    public void setAvailableItemsList(Map<Item, Integer> availableItemsList) {
        this.availableItemsList = availableItemsList;
    }

    public Map<Item, Address> getShippedList() {
        return shippedList;
    }

    public void setShippedList(Map<Item, Address> shippedList) {
        this.shippedList = shippedList;
    }

    public Map<Item, Address> getShippingList() {
        return shippingList;
    }

    public void setShippingList(Map<Item, Address> shippingList) {
        this.shippingList = shippingList;
    }

    public boolean addCustomer(final Customer customer) {
        if (!this.customers.contains(customer)) {
            return this.customers.add(customer);
        }

        return false;
    }

    // -- helper methods --
    public Customer getCustomerById(final String id) {
        Customer result = null;
        for (final Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                result = customer;
            }
        }

        return result;
    }

    public boolean removeCustomerById(final String id) {
        for (final Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                return this.customers.remove(customer);
            }
        }

        return false;
    }

    public boolean addItem(final Item item) {
        if (!this.items.contains(item)) {
            return this.items.add(item);
        }

        return false;
    }

    public Item getItemById(final String id) {
        Item result = null;
        for (final Item item : this.items) {
            if (item.getProductId().equals(id)) {
                result = item;
            }
        }

        return result;
    }

    public boolean removeItemById(final String id) {
        for (final Item item : this.items) {
            if (item.getProductId().equals(id)) {
                return this.items.remove(item);
            }
        }

        return false;
    }

    //TODO implement increase decrease availability of items
    //TODO implement increase decrease shipping items
    //TODO implement increase shipped items.

}
