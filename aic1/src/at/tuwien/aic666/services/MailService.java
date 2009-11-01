package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.UnknownCustomerFault;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class MailService {

    public boolean notifyCustomer(Customer customer, String message) {
        //TODO check whether customer exists and throw fault if not
        if (!this.existsCustomer(customer)) {
            throw new UnknownCustomerFault("The customer with id" + customer.getId() + "is not known to the system");
        }
        
        System.out.println(message);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Random().nextBoolean();
    }

    private boolean existsCustomer(final Customer customer) {
        return DataBaseMock.getInstance().getCustomerById(customer.getId()) != null ? true : false;
    }
}
