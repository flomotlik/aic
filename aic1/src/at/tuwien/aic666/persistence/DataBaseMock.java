package at.tuwien.aic666.persistence;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author peter
 */
public class DataBaseMock {

    private Map<String, Customer> customers;
    private Map<String, Integer> availableItems;
    private Collection<Item> shippingList;
    private Collection<Item> shippedList;
    private static DataBaseMock uniqueInstance;

    /**
     * @author kevin
     * @return
     */
    private Timer shippingTimer;
    
    public static synchronized DataBaseMock getInstance() {
        if (DataBaseMock.uniqueInstance == null) {
            DataBaseMock.uniqueInstance = new DataBaseMock();
        }

        return DataBaseMock.uniqueInstance;
    }

    private DataBaseMock() {
        this.customers = new HashMap<String, Customer>();
        //this.items = new ArrayList<Item>();
        this.availableItems = new HashMap<String, Integer>();
        this.shippedList = new ArrayList<Item>();
        this.shippingList = new ArrayList<Item>();
        this.shippingTimer = new Timer();
    }

    public synchronized Map<String, Customer> getCustomers() {
        return customers;
    }

    public synchronized void setCustomers(Map<String, Customer> customers) {
        this.customers = customers;
    }

    public synchronized Map<String, Integer> getAvailableItemsList() {
        return availableItems;
    }

    public synchronized void setAvailableItemsList(Map<String, Integer> availableItemsList) {
        this.availableItems = availableItemsList;
    }

    public synchronized Collection<Item> getShippedList() {
        return shippedList;
    }

    public synchronized void setShippedList(Collection<Item> shippedList) {
        this.shippedList = shippedList;
    }

    public synchronized Collection<Item> getShippingList() {
        return shippingList;
    }

    public synchronized void setShippingList(Collection<Item> shippingList) {
        this.shippingList = shippingList;
    }

    public synchronized boolean addCustomer(final Customer customer) {
        if (!this.customers.containsValue(customer)) {
            this.customers.put(customer.getId(), customer);
            return true;
        }
        return false;
    }

    // -- helper methods --
    public synchronized Customer getCustomerById(final String id) {
        return this.customers.get(id);
    }

    public synchronized boolean removeCustomerById(final String id) {
        return this.customers.remove(id) != null;
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

    /**
     * TODO make sure this is threadsafe
     * maybe it's easier to put all items into a HashMap that maps onto Boolean
     * to show whether it has been shipped or not?
     * @author kevin
     */
    public synchronized boolean addShippingItem(final Item item) {
        boolean toReturn = this.shippingList.add(item);
        this.shippingTimer.schedule(new TimerTask() {
            public void run() {
                shippingList.remove(item);
                addShippedItem(item);
                System.out.println("SHIPPED " + item.getProductId());
            }}, 3000); // move item in 3 seconds
        return toReturn;
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

    public synchronized void clear() {
        this.customers.clear();
        this.availableItems.clear();
        this.shippingList.removeAll(this.shippingList);
        this.shippedList.removeAll(this.shippedList);

    }
}
