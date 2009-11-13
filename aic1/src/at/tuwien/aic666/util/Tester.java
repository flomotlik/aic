package at.tuwien.aic666.util;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.services.CustomerManagement;
import at.tuwien.aic666.services.CustomerManagementSoap;
import at.tuwien.aic666.services.INotify;
import at.tuwien.aic666.services.ServiceStarter;
import javax.xml.ws.soap.SOAPFaultException;
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
        tester.testSMSService();
        tester.testMailService();
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
        c.setId("1");
        customerManagementService.createCustomer(c);
    }

    private void testSMSService() {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(INotify.class);
        svrFactory.setAddress(ServiceStarter.smsAddress);
        INotify service = (INotify) svrFactory.create();
        boolean notifyCustomer = service.notifyCustomer(new Customer("1"), "Message");
        System.out.println("Notified: " + notifyCustomer);
        try {
            service.notifyCustomer(new Customer("2"), "Message");
            Assert.fail();
        } catch (SOAPFaultException e) {
            System.out.println(e.getFault().toString() + " caught correctly: " + e.getMessage());
        }
        try {
            service.notifyCustomer(new Customer("1"), "MessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessage" +
                    "MessageMessageMessageMessageMessageMessageMessageMessageMessage");
            Assert.fail();
        } catch (SOAPFaultException e) {
            System.out.println(e.getFault() + " caught correctly: " + e.getMessage());
        }
    }

    private void testMailService() {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(INotify.class);
        svrFactory.setAddress(ServiceStarter.mailAddress);
        INotify service = (INotify) svrFactory.create();
        boolean notifyCustomer = service.notifyCustomer(new Customer("1"), "Message");
        System.out.println("Notified: " + notifyCustomer);
        try {
            service.notifyCustomer(new Customer("2"), "Message");
            Assert.fail();
        } catch (SOAPFaultException e) {
            System.out.println(e.getFault().toString() + " caught correctly: " + e.getMessage());
        }
    }
}
