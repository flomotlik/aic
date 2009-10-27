package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class MailService {

    public boolean notifyCustomer(Customer customer, String message) {
        System.out.println(message);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Random().nextBoolean();
    }
}
