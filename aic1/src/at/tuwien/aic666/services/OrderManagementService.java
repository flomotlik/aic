package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.ItemUnavailableFault;
import java.util.Collection;
import java.util.Date;
import javax.jws.WebService;

/**
 *
 * @author peter
 */

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

        return quantity >= item.getQuantity() ? true : false;
    }

    //please note.. not an array... is this important...
    public Order placeOrder(final Collection<Item> items, final Customer customer) {
        final Order order = new Order();
        
        order.setCustomer(customer);
        order.setId("order" + OrderManagementService.counter++);
        order.setOrderDate(new Date());

        for (final Item i : items) {
            if (i.getQuantity() <= this.db.getAvailableItemsList().get(i.getProductId())) {
                this.db.decreaseItemsAvailable(i, i.getQuantity());
                this.db.addShippingItem(i);
                final Collection<Item> currentItems = order.getItems();
                currentItems.add(i);
                order.setItems(currentItems);
            } else {
                throw new ItemUnavailableFault("Item with id " + i.getProductId() + "is not available");
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
}
