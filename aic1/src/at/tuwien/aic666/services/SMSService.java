package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.MessageTooLongFault;
import at.tuwien.aic666.util.UnknownCustomerFault;
import java.util.Random;
import javax.jws.WebService;

/**
 * @author peter
 */
@WebService(endpointInterface = "at.tuwien.aic666.services.INotify",
serviceName = "NotificationService", targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT")
public class SMSService implements INotify {

    public boolean notifyCustomer(Customer customer, String message) {

        if (!existsCustomer(customer)) {
            throw new UnknownCustomerFault("The customer with id " + customer.getId() + " is not known to the system");
        }

        if (message.length() > 160) {
            throw new MessageTooLongFault("The message is longer than 160 characters");
        }

        System.out.println("SMSService: " + message);
        return new Random().nextBoolean();
    }

    private boolean existsCustomer(final Customer customer) {
        return DataBaseMock.getInstance().getCustomerById(customer.getId()) != null ? true : false;
    }
}
