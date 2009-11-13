/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import javax.jws.WebService;

/**
 *
 * @author florian
 */
@WebService
public interface ISMSService {

    boolean notifyCustomer(Customer customer, String message);

}
