package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.persistence.DataBaseMock;
import at.tuwien.aic666.util.UnknownCustomerFault;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author kevin
 */
@Path("/customers/")
@Produces({"application/json", "application/xml"})
public class CustomerManagementService implements CustomerManagement {

    DataBaseMock db;

    public CustomerManagementService() {
        this.db = DataBaseMock.getInstance();
    }

    @Context
    private HttpHeaders httpHeaders;

    // TODO I think we somehow have to set a default-operation?
    @PUT
    @Path("/create")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Customer createCustomer(Customer c) {
        writeHttpHeaders();
        // TODO return only id or full customer?
        System.out.println("Creating Customer with id:" + c.getId());
        if (this.db.addCustomer(c)) {
            return c;
        } else {
            return null;
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces({"application/json", "application/xml"})
    public Customer getCustomer(@PathParam("id") String id) {
        System.out.println("Retrieving info for customer " + id);
        Customer c = this.db.getCustomerById(id);
        if (c == null) {
            throw new UnknownCustomerFault("Customer with " + id + " is unknown");
        }
        return c;
    }

    @POST
    @Path("/update/")
    @Consumes({"application/json", "application/xml"})
    public Response updateCustomer(Customer c) {
        writeHttpHeaders();
        // TODO check whether this really overwrites any preexisting entries
        // TODO any exceptions if user not known?
        System.out.println("Updating info for customer: " + c.getId());
        this.db.addCustomer(c);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteCustomer(@PathParam("id") String id) {
        System.out.println("Deleting customer " + id);
        this.db.removeCustomerById(id); // TODO throw exception instead of returning false when user not found?
        return Response.status(Response.Status.OK).build();
    }

    private void writeHttpHeaders() {
        System.out.println("MediaType: " + httpHeaders.getMediaType());
        System.out.println("Accept: " + httpHeaders.getRequestHeader("accept"));
        System.out.println("Mime-Type: " + httpHeaders.getRequestHeader("content-type"));
    }
}
