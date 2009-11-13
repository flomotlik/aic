package at.tuwien.aic666;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.services.CustomerManagement;
import at.tuwien.aic666.services.ServiceStarter;
import junit.framework.Assert;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author florian
 */
public class Tester {

    public static void main(String args[]) {
        Tester tester = new Tester();
        tester.testCustomerManagementService();
    }

    private void testCustomerManagementService() {
        CustomerManagement customerManagementService = JAXRSClientFactory.create(ServiceStarter.customerAddress, CustomerManagement.class);
        Customer c = new Customer();
        c.setId("1");
        c.setName("Name");
        customerManagementService.createCustomer(c);
        Customer c2 = customerManagementService.getCustomer("1");
        Assert.assertEquals("1", c2.getId());
        Assert.assertEquals("Name", c2.getName());
        customerManagementService.deleteCustomer("1");
        Customer c3 = customerManagementService.getCustomer("1");
        Assert.assertEquals(null, c3);
    }
}
