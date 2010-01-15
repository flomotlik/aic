package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Address;
import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.datamodel.PaymentPreference;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.ItemUnavailableFault;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebService;

/**
 *
 * @author peter
 */
@WebService(endpointInterface = "at.tuwien.aic666.services.IOrderManagementService", serviceName = "OrderManagementService")
public class OrderManagementService implements IOrderManagementService {

    private static int counter = 0;
    private DataBaseMock db;

    public OrderManagementService() {
        this.db = DataBaseMock.getInstance();
    }

    public boolean checkAvailability(Item item) {
        System.out.println("Checking item with id: " + item.getProductId());
        Integer quantity = this.db.getAvailableItemsList().get(item.getProductId());

        if (quantity == null) {
            throw new ItemUnavailableFault("Item with id " + item.getProductId() + " is unknown");
        }

        //System.out.println("DBQuantity: " + quantity + "\nQnt: " + item.getQuantity());
        System.out.println("Available: " + (quantity >= item.getQuantity()));
        return quantity >= item.getQuantity();
    }

    public Order placeOrder(Item[] items, Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setId("order" + OrderManagementService.counter++);
        order.setOrderDate(new Date());
        System.out.println("Placing a new order...");

        //check whether all items are available...
        Item item = this.checkItemsAvailability(items);
        if (item != null) {
            //Does a Soap Fault break the method like an exception or continues...?
            throw new ItemUnavailableFault("Item with id " + item.getProductId() + " is not available");
        }

        for (Item i : items) {
            if (i.getQuantity() <= this.db.getAvailableItemsList().get(i.getProductId())) {
                this.db.decreaseItemsAvailable(i, i.getQuantity());
                this.db.addShippingItem(i);
                order.getItems().add(i);
            }
        }

        System.out.println("A new order with id: '" + order.getId() + "' placed.");
        this.db.saveOrder(order);
        return order;
    }

    public boolean isFinished(Order order) {
        boolean finished = false;

        if (this.checkItems(order)) {
            finished = true;
        }

         System.out.println("IsFinished " + order.getId() + ": " + finished);
        return finished;
    }

    public void insertTestData() {
        this.db.clear();

        Address add1 = new Address("add1", "street1", "city1", "666", 6, 6);
        Address add2 = new Address("add2", "street2", "city2", "1000", 1, 2);

        Customer cust1 = new Customer("1");
        cust1.setName("Max Mustermann");
        cust1.setPreference(PaymentPreference.BANK_TRANSFER);
        cust1.setAddress(add1);

        Customer cust2 = new Customer("2");
        cust2.setName("John Doe");
        cust2.setPreference(PaymentPreference.CREDIT_CARD);
        cust2.setAddress(add2);

        Customer cust3 = new Customer("3");
        cust3.setName("John 3");
        cust3.setPreference(PaymentPreference.CREDIT_CARD);
        cust3.setAddress(add2);

        Map<String, Customer> customers = new HashMap<String, Customer>();
        customers.put("1", cust1);
        customers.put("2", cust2);
        customers.put("3", cust3);


        Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();

        item1.setProductId("item1");
        item2.setProductId("item2");
        item3.setProductId("item3");

        this.db.increaseItemsAvailable(item1, 105);
        this.db.increaseItemsAvailable(item2, 3);
        this.db.increaseItemsAvailable(item3, 100);
        this.db.setCustomers(customers);


    }

    /**
     * Returns the first unavailable item of the passed array or null if all
     * items are available
     * @param items the items to check
     * @return read javadoc....
     */
    private Item checkItemsAvailability(Item[] items) {
        for (Item i : items) {
            if (i.getQuantity() > this.db.getAvailableItemsList().get(i.getProductId())) {
                return i;
            }
        }
        return null;
    }

    private boolean checkItems(Order order) {
        final Order tmp = this.db.getOrderById(order.getId());

        if (tmp != null) {
            for (Item i : tmp.getItems()) {
                boolean item = false;
                for (Item shipped : db.getShippedList()) {
                    if (shipped.getProductId().equals(i.getProductId())) {
                        item = true;
                        break;
                    }
                }

                if (item) {
                    continue;
                } else {
                    return false;
                }

            }

            return true;
        }

        return false;

    }
}
