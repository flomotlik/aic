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
public interface CustomerManagementSoap{

    Customer createCustomer(Customer c);

    void deleteCustomer(String id);

    Customer getCustomer(String id);

    void updateCustomer(Customer c);
}
