/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import javax.jws.WebService;

/**
 *
 * @author kevin
 */
// FIXME namespace/port/servicename
@WebService(targetNamespace = "http://infosys.tuwien.ac.at/ait09/ass1/dto/notification",
portName = "NotificationPT", serviceName = "NotificationService")
public class BankingService {

    public void chargeCreditCard(Customer c, Integer amount) {
        System.out.println("Charging " + amount.toString() + " on customer's credit card");
    }

    public void doBankTransfer(Customer c, Integer amount) {
        System.out.println("Transferring " + amount.toString() + " from customer's bank account");
    }
}
