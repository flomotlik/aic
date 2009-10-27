package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import java.util.Random;
import javax.jws.WebService;

@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class SMSService {

    public boolean notifyCustomer(Customer customer, String message) {
        System.out.println(message);
        return new Random().nextBoolean();
    }
}
