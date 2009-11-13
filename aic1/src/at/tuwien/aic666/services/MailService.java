package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.UnknownCustomerFault;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 * @author peter
 */
@WebService(endpointInterface = "at.tuwien.aic666.services.INotify",
serviceName = "NotificationService", targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT")
public class MailService implements INotify {

    public boolean notifyCustomer(Customer customer, String message) {
        //TODO check whether customer exists and throw fault if not
        if (!this.existsCustomer(customer)) {
            throw new UnknownCustomerFault("The customer with id" + customer.getId() + "is not known to the system");
        }

        System.out.println("MailSerivce: " + message);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Slept");
        return new Random().nextBoolean();
    }

    private boolean existsCustomer(final Customer customer) {
        return DataBaseMock.getInstance().getCustomerById(customer.getId()) != null ? true : false;
    }
}
