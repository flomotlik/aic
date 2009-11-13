package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import javax.jws.WebService;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

/**
 * TODO this thing is definitely not finished and completely untested!
 *
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

    public boolean updateCustomer(Customer c) {
        return this.customerManagementService.updateCustomer(c);
    }

    public boolean deleteCustomer(String id) {
        return this.customerManagementService.deleteCustomer(id);
    }
}
