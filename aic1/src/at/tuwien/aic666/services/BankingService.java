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
@WebService(endpointInterface = "at.tuwien.aic666.services.IBankingService", serviceName = "BankingService")
public class BankingService {

    public void chargeCreditCard(Customer c, Integer amount) {
        System.out.println("Charging " + amount.toString() + " on customer's credit card");
    }

    public void doBankTransfer(Customer c, Integer amount) {
        System.out.println("Transferring " + amount.toString() + " from customer's bank account");
    }
}
