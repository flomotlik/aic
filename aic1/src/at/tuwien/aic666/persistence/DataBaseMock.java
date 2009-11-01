package at.tuwien.aic666.persistence;

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
    private Map<String, Integer> availableItems;
    private Collection<Item> shippingList;
    private Collection<Item> shippedList;
    private static DataBaseMock uniqueInstance;

    
    public static synchronized DataBaseMock getInstance() {
        if (DataBaseMock.uniqueInstance == null) {
            DataBaseMock.uniqueInstance = new DataBaseMock();
        }

        return DataBaseMock.uniqueInstance;
    }

    private DataBaseMock() {
        this.customers = new ArrayList<Customer>();
        //this.items = new ArrayList<Item>();
        this.availableItems = new HashMap<String, Integer>();
        this.shippedList = new ArrayList<Item>();
        this.shippingList = new ArrayList<Item>();

    }

    // -- getter and setter --
    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    public Map<String, Integer> getAvailableItemsList() {
        return availableItems;
    }

    public void setAvailableItemsList(Map<String, Integer> availableItemsList) {
        this.availableItems = availableItemsList;
    }

    public Collection<Item> getShippedList() {
        return shippedList;
    }

    public void setShippedList(Collection<Item> shippedList) {
        this.shippedList = shippedList;
    }

    public Collection<Item> getShippingList() {
        return shippingList;
    }

    public void setShippingList(Collection<Item> shippingList) {
        this.shippingList = shippingList;
    }

    public synchronized boolean addCustomer(final Customer customer) {
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

    public synchronized boolean removeCustomerById(final String id) {
        for (final Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                return this.customers.remove(customer);
            }
        }

        return false;
    }

    public synchronized void increaseItemsAvailable(final Item item, final int increase) {
        final Integer currentQuantity = this.availableItems.get(item.getProductId());
        final Integer newQuantity;

        if (currentQuantity == null) {
            newQuantity = increase;
        } else {
            newQuantity = currentQuantity + increase;
        }

        this.availableItems.put(item.getProductId(), newQuantity);
    }

    public synchronized void decreaseItemsAvailable(final Item item, final int decrease) {
        final Integer currentQuantity = this.availableItems.get(item.getProductId());
        final Integer newQuantity;

        if (currentQuantity == null) {
            newQuantity = 0;
        } else {
            if (currentQuantity >= decrease) {
                newQuantity = currentQuantity - decrease;
            } else {
                newQuantity = 0;
            }
        }

        this.availableItems.put(item.getProductId(), newQuantity);
    }

    public synchronized boolean addShippingItem(final Item item) {
        return this.shippingList.add(item);
    }

    public synchronized boolean removeShippingItemById(final String id) {
        for (final Item item : this.shippingList) {
            if (item.getProductId().equals(id)) {
                return this.shippingList.remove(item);
            }
        }

        return false;
    }

    public synchronized boolean addShippedItem(final Item item) {
        return this.shippedList.add(item);
    }
}
