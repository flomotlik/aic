package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Address;
import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.datamodel.PaymentPreference;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.ItemUnavailableFault;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

    public boolean checkAvailability(final Item item) {
        final Integer quantity = this.db.getAvailableItemsList().get(item.getProductId());

        return quantity >= item.getQuantity();
    }

    public Order placeOrder(final Item[] items, final Customer customer) {
        final Order order = new Order();

        order.setCustomer(customer);
        order.setId("order" + OrderManagementService.counter++);
        order.setOrderDate(new Date());

        //check whether all items are available...
        final Item item = this.checkItemsAvailability(items);
        if (item != null) {
            //Does a Soap Fault break the method like an exception or continues...?
            throw new ItemUnavailableFault("Item with id " + item.getProductId() + "is not available");
        }

        for (final Item i : items) {
            if (i.getQuantity() <= this.db.getAvailableItemsList().get(i.getProductId())) {
                this.db.decreaseItemsAvailable(i, i.getQuantity());
                this.db.addShippingItem(i);
                order.getItems().add(i);
            }
        }

        return order;
    }

    public boolean isFinished(final Order order) {
        boolean finished = false;

        for (final Item i : order.getItems()) {
            if (db.getShippedList().contains(i)) {
                finished = true;
            }
        }

        return finished;
    }

    public void insertTestData() {
        this.db.clear();

        final Address add1 = new Address("add1", "street1", "city1", "666", 6, 6);
        final Address add2 = new Address("add2", "street2", "city2", "1000", 1, 2);

        final Customer cust1 = new Customer("1");
        cust1.setName("Max Mustermann");
        cust1.setPreference(PaymentPreference.BANK_TRANSFER);
        cust1.setAddress(add1);

        final Customer cust2 = new Customer("2");
        cust2.setName("Joh Doe");
        cust2.setPreference(PaymentPreference.CREDIT_CARD);
        cust2.setAddress(add2);

        final Map<String, Customer> customers = new HashMap<String, Customer>();
        customers.put("1", cust1);
        customers.put("2", cust2);


        final Item item1 = new Item();
        final Item item2 = new Item();
        item1.setProductId("item1");
        item1.setQuantity(2);
        item1.setSingleUnitPrice(new BigDecimal(10));

        item2.setProductId("item2");
        item1.setQuantity(2);
        item1.setSingleUnitPrice(new BigDecimal(10));

        this.db.increaseItemsAvailable(item1, 5);
        this.db.increaseItemsAvailable(item2, 1);
        this.db.setCustomers(customers);


    }

    /**
     * Returns the first unavailable item of the passed array or null if all
     * items are available
     * @param items the items to check
     * @return read javadoc....
     */
    private Item checkItemsAvailability(final Item[] items) {
        for (final Item i : items) {
            if (i.getQuantity() > this.db.getAvailableItemsList().get(i.getProductId())) {
                return i;
            }
        }
        return null;
    }
}
