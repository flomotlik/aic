/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import javax.jws.WebService;

/**
 *
 * @author peter
 */
@WebService
public interface IOrderManagementService {

    void insertTestData();

    boolean isFinished(Order order);

    Order placeOrder(Item[] items, Customer customer);

    boolean checkAvailability(Item item);
}
