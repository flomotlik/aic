package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.persistence.DataBaseMock;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * TODO this thing is definitely not finished and completely untested!
 *
 * @author kevin
 */
@Path("/customers/")
@Produces({"application/json", "application/xml"})
public class CustomerManagementService implements CustomerManagement {

    DataBaseMock db;

    public CustomerManagementService() {
        this.db = DataBaseMock.getInstance();
    }

    // TODO I think we somehow have to set a default-operation?

    @PUT
    @Path("/create")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Customer createCustomer(Customer c) {
        // TODO return only id or full customer?
        System.out.println("Creating Customer with id:" + c.getId());
        if(this.db.addCustomer(c)){
            return c;
        }else{
            return null;
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces({"application/json", "application/xml"})
    public Customer getCustomer(@PathParam("id") String id) {
        System.out.println("Retrieving info for customer " + id);
        return this.db.getCustomerById(id);
    }

    @POST
    @Path("/update/")
    @Consumes({"application/json", "application/xml"})
    public void updateCustomer(Customer c) {
        // TODO check whether this really overwrites any preexisting entries
        // TODO any exceptions if user not known?
        this.db.addCustomer(c);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteCustomer(@PathParam("id") String id) {
        System.out.println("Deleting customer " + id);
        this.db.removeCustomerById(id); // TODO throw exception instead of returning false when user not found?
    }

}
