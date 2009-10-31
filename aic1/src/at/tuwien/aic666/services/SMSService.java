package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.util.MessageTooLongFault;
import java.util.Random;
import javax.jws.WebService;

@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class SMSService {

    public boolean notifyCustomer(Customer customer, String message) {
        //TODO check whether customer exists and throw fault if not
        if (message.length() > 160) {
            throw new MessageTooLongFault("The message is longer than 160 characters");
        }

        System.out.println(message);
        return new Random().nextBoolean();
    }
}
