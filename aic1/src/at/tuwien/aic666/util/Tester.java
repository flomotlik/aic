package at.tuwien.aic666.util;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.services.CustomerManagement;
import at.tuwien.aic666.services.CustomerManagementSoap;
import at.tuwien.aic666.services.ISMSService;
import at.tuwien.aic666.services.ServiceStarter;
import junit.framework.Assert;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

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
        tester.testCustomerManagementServiceSoapWrapper();
    }

    private void testCustomerManagementService() {
        System.out.println("Testing Customer JaxRS Service");
        CustomerManagement customerManagementService = JAXRSClientFactory.create(ServiceStarter.customerAddress, CustomerManagement.class);
        Customer c = new Customer();
        final String id = "CustomerManagementService";
        c.setId(id);
        c.setName("Name");
        customerManagementService.createCustomer(c);
        Customer c2 = customerManagementService.getCustomer(id);
        Assert.assertEquals(id, c2.getId());
        Assert.assertEquals("Name", c2.getName());
        c.setName("NewName");
        customerManagementService.updateCustomer(c);
        Customer c3 = customerManagementService.getCustomer(id);
        Assert.assertEquals("NewName", c3.getName());
        customerManagementService.deleteCustomer(id);
        Customer c4 = customerManagementService.getCustomer(id);
        Assert.assertEquals(null, c4);
    }

    private void testCustomerManagementServiceSoapWrapper() {
        System.out.println("Testing Customer JaxRS SoapWrapper Service");
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(CustomerManagementSoap.class);
        svrFactory.setAddress(ServiceStarter.customerSoapAddress);
        CustomerManagementSoap customerManagementService = (CustomerManagementSoap) svrFactory.create();
        final String id = "CustomerManagementServiceWrapper";
        Customer c = new Customer();
        c.setId(id);
        c.setName("Name");
        customerManagementService.createCustomer(c);
        Customer c2 = customerManagementService.getCustomer(id);
        Assert.assertEquals(id, c2.getId());
        Assert.assertEquals("Name", c2.getName());
        c.setName("NewName");
        customerManagementService.updateCustomer(c);
        Customer c3 = customerManagementService.getCustomer(id);
        Assert.assertEquals("NewName", c3.getName());
        customerManagementService.deleteCustomer(id);
        Customer c4 = customerManagementService.getCustomer(id);
        Assert.assertEquals(null, c4);
    }
}
