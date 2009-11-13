package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import javax.jws.WebService;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

/**
 * @author flo
 */
@WebService(endpointInterface = "at.tuwien.aic666.services.CustomerManagementSoap",
serviceName = "CustomerManagementServiceSoap")
public class CustomerManagementServiceSoap implements CustomerManagementSoap {

    private CustomerManagement customerManagementService;

    public CustomerManagementServiceSoap() {
        this.customerManagementService = JAXRSClientFactory.create(ServiceStarter.customerAddress, CustomerManagement.class);
    }

    public Customer createCustomer(Customer c) {
        return customerManagementService.createCustomer(c);
    }

    public Customer getCustomer(String id) {
        return this.customerManagementService.getCustomer(id);
    }

    public void updateCustomer(Customer c) {
        this.customerManagementService.updateCustomer(c);
    }

    public void deleteCustomer(String id) {
        this.customerManagementService.deleteCustomer(id);
    }
}
