/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.services;

import at.tuwien.aic666.datamodel.Customer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author florian
 */
@Path("/customers/")
@Produces({"application/json", "application/xml"})
public interface CustomerManagement {

    @PUT
    @Path(value = "/create")
    @Consumes(value = {"application/json", "application/xml"})
    @Produces(value = {"application/json", "application/xml"})
    Customer createCustomer(Customer c);

    @DELETE
    @Path(value = "/delete/{id}")
    void deleteCustomer(@PathParam("id")
    String id);

    @GET
    @Path(value = "/get/{id}")
    @Produces(value = {"application/json", "application/xml"})
    Customer getCustomer(@PathParam(value = "id")
    String id);

    @POST
    @Path(value = "/update/")
    @Consumes(value = {"application/json", "application/xml"})
    void updateCustomer(Customer c);

}
