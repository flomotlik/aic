package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.ItemUnavailableFault;
import java.util.Date;
import javax.jws.WebService;

/**
 *
 * @author peter
 */
// FIXME namespace/port/servicename
@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class OrderManagementService {

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
        //TODO implement method stub...
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
