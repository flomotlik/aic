package at.tuwien.aic666.util;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.services.CustomerManagement;
import at.tuwien.aic666.services.CustomerManagementSoap;
import at.tuwien.aic666.services.IBankingService;
import at.tuwien.aic666.services.INotify;
import at.tuwien.aic666.services.IOrderManagementService;
import at.tuwien.aic666.services.ServiceStarter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.xml.ws.soap.SOAPFaultException;
import junit.framework.Assert;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.remoting.soap.SoapFaultException;

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
        tester.testBankingService();
        tester.testCheckAvailability();
        tester.testPlaceOderWithAvailableItems();
        tester.testPlaceOrderWithUnavailableItems();
    }

    private void testCustomerManagementService() {
        System.out.println("Testing Customer JaxRS Service");
        CustomerManagement customerManagementService = JAXRSClientFactory.create(ServiceStarter.customerAddress, CustomerManagement.class);
        WebClient.client(customerManagementService).type("application/xml");
        WebClient.client(customerManagementService).accept("application/xml");
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

    private void testBankingService() {
        System.out.println("Testing Banking Service with username token authentication");
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IBankingService.class);
        svrFactory.setAddress(ServiceStarter.bankingAddress);

        // Security properties
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        // Specify our username, which isn't currently tested on the server-side anyway
        outProps.put(WSHandlerConstants.USER, "user-joe");
        // hashed password use:
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // Callback used to retrieve (correct) password for given user.
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, TestPasswordCallback.class.getName());
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
        svrFactory.getOutInterceptors().add(wssOut);

        IBankingService bankingService = (IBankingService) svrFactory.create();
        // We don't actually need a customer because the BankingService is just a dummy
        bankingService.chargeCreditCard(null, 20000);
        bankingService.doBankTransfer(null, 40000);

        // The same thing again but this time with an incorrect password
        svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IBankingService.class);
        svrFactory.setAddress(ServiceStarter.bankingAddress);
        // Security properties
        outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        // Specify our username, which isn't currently tested on the server-side anyway
        outProps.put(WSHandlerConstants.USER, "user-joe");
        // hashed password use:
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // Callback used to retrieve (correct) password for given user.
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, TestIncorrectPasswordCallback.class.getName());
        wssOut = new WSS4JOutInterceptor(outProps);
        svrFactory.getOutInterceptors().add(wssOut);

        bankingService = (IBankingService) svrFactory.create();
        try {
            bankingService.chargeCreditCard(null, 20000);
            Assert.fail();
        } catch (SOAPFaultException e) {
            System.out.println(e.getFault().toString() + " caught correctly: " + e.getMessage());
        }
    }

    private void testCheckAvailability() {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IOrderManagementService.class);
        svrFactory.setAddress(ServiceStarter.orderManagementAddress);
        IOrderManagementService service = (IOrderManagementService) svrFactory.create();

        service.insertTestData();

        Item item = new Item();
        item.setProductId("item1");
        item.setQuantity(2);

        Item nonavail = new Item();
        nonavail.setProductId("item2");
        nonavail.setQuantity(2);

        Item nonexist = new Item();
        nonexist.setQuantity(100);
        nonexist.setProductId("non-exist");
        try {
            Assert.assertEquals(true, service.checkAvailability(item));
            Assert.assertEquals(false, service.checkAvailability(nonexist));
            Assert.assertEquals(false, service.checkAvailability(nonavail));

        } catch (SoapFaultException e) {
            Assert.assertTrue(false);
            System.out.println("failed");
        }
    }

    private void testPlaceOderWithAvailableItems() {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IOrderManagementService.class);
        svrFactory.setAddress(ServiceStarter.orderManagementAddress);
        IOrderManagementService service = (IOrderManagementService) svrFactory.create();

        service.insertTestData();

        Item item = new Item();
        item.setProductId("item1");
        Assert.assertEquals(true, service.checkAvailability(item));

        item.setQuantity(2);
        item.setSingleUnitPrice(new BigDecimal(10));

        Item[] items = {item};
        final Order order = service.placeOrder(items, DataBaseMock.getInstance().getCustomerById("1"));
        Assert.assertEquals(order.getCustomer(), DataBaseMock.getInstance().getCustomerById("1"));
        Assert.assertEquals(order.getItems().iterator().next().getQuantity(), item.getQuantity());
        System.out.println("Order placed, waiting for shippment");

        int i = 0;
        while (!service.isFinished(order)) {
            if (i % 100 == 0) {
                System.out.println("Polling order status: " + service.isFinished(order));
            }
            i++;
        }

        System.out.println("Polling order status: " + service.isFinished(order));
    }

    private void testPlaceOrderWithUnavailableItems() {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IOrderManagementService.class);
        svrFactory.setAddress(ServiceStarter.orderManagementAddress);
        IOrderManagementService service = (IOrderManagementService) svrFactory.create();

        service.insertTestData();

        final Item item = new Item();
        item.setProductId("item2");

        item.setQuantity(2);
        item.setSingleUnitPrice(new BigDecimal(10));

        Item[] items = {item};
        try {
            service.placeOrder(items, DataBaseMock.getInstance().getCustomerById("1"));
            Assert.fail();
        } catch (SOAPFaultException e) {
            System.out.println(e.getFault().toString() + " caught correctly: " + e.getMessage());
            System.exit(1);
        }
    }
}
