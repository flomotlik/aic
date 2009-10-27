/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.tuwien.aic666.jaxb;

import at.tuwien.aic666.datamodel.Address;
import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.datamodel.Item;
import at.tuwien.aic666.datamodel.Order;
import at.tuwien.aic666.datamodel.PaymentPreference;
import at.tuwien.aic666.util.DateLongAdapter;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * only for testing purposes not a real unit test.
 * @author peter
 */
public class JAXBAnnotationsTest {
    public static final String BUILDADDRESSXML = "build/address.xml";
    public static final String BUILDCUSTOMERXML = "build/customer.xml";
    public static final String BUILDITEM1XML = "build/item1.xml";
    public static final String BUILDORDERXML = "build/order.xml";
    public static final String BUILDPREFXML = "build/pref.xml";
    private static final String BUILDITEM2XML = "build/item2.xml";

    private Item item1;
    private Item item2;
    private Customer customer;
    private PaymentPreference pref;
    private Order order;
    private Address address;

    @Before
    public void doBeforeEachTest() {
        this.item1 = new Item();
        this.item2 = new Item();
        this.customer = new Customer();
        this.pref = PaymentPreference.CREDIT_CARD;
        this.order = new Order();
        this.address = new Address();

        this.generateTestData();
    }

    @Test
    public void shouldGenerateXml() {
        try {
            final JAXBContext context = JAXBContext.newInstance(Item.class, Order.class, Customer.class, Address.class, PaymentPreference.class);
            final Marshaller marshaller = context.createMarshaller();

            marshaller.marshal(this.customer, new File(BUILDCUSTOMERXML));
            marshaller.marshal(this.address, new File(BUILDADDRESSXML));
            marshaller.marshal(this.pref, new File(BUILDPREFXML));
            marshaller.marshal(this.order, new File(BUILDORDERXML));
            marshaller.marshal(this.item1, new File(BUILDITEM1XML));
            marshaller.marshal(this.item2, new File(BUILDITEM2XML));

        } catch (final JAXBException ex) {
            Logger.getLogger(JAXBAnnotationsTest.class.getName()).log(Level.SEVERE, "An error occurred", ex);
            Assert.fail("Exception was thrown");
        }
    }

    @Test
    public void shouldGenerateDomainObjectsFromXml() {
        try {
            final JAXBContext context = JAXBContext.newInstance(Item.class, Order.class, Customer.class, Address.class, PaymentPreference.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setAdapter(new DateLongAdapter());


            final Customer cust = (Customer) unmarshaller.unmarshal(new File(BUILDCUSTOMERXML));
            final Address adr = (Address) unmarshaller.unmarshal(new File(BUILDADDRESSXML));
            final Order ord = (Order) unmarshaller.unmarshal(new File(BUILDORDERXML));
            final Item itm1 = (Item) unmarshaller.unmarshal(new File(BUILDITEM1XML));
            final Item itm2 = (Item) unmarshaller.unmarshal(new File(BUILDITEM2XML));
            final PaymentPreference prf = (PaymentPreference) unmarshaller.unmarshal(new File(BUILDPREFXML));

            itm1.afterUnmarshal(unmarshaller, ord);
            itm2.afterUnmarshal(unmarshaller, ord);

            Assert.assertEquals(this.customer.getId(), cust.getId());
            Assert.assertEquals(this.customer.getName(), cust.getName());
            Assert.assertEquals(this.address.getCity(), adr.getCity());
            Assert.assertEquals(this.address.getId(), adr.getId());
            Assert.assertEquals(this.item1.getProductId(), itm1.getProductId());
            Assert.assertEquals(this.item2.getOrder().getId(), itm2.getOrder().getId());
            Assert.assertEquals(this.item2.getOrder().getOrderDate().getTime(), itm2.getOrder().getOrderDate().getTime());
            Assert.assertEquals(this.order.getCustomer().getName(), ord.getCustomer().getName());
            Assert.assertTrue(ord.getOrderDate() instanceof Date);
            Assert.assertEquals(this.order.getOrderDate().getTime(), ord.getOrderDate().getTime());
            Assert.assertEquals(this.pref.name(), prf.name());

        } catch (JAXBException ex) {
            Logger.getLogger(JAXBAnnotationsTest.class.getName()).log(Level.SEVERE, "Error", ex);
            Assert.fail("Exception was thrown");
        }

    }

    // delete this annotation if you want to see the xmls...
    @AfterClass
    public static void deleteResources() {
        final File resources = new File("test/resources");
        if (resources.exists() && resources.isDirectory()) {
            for (final File f : resources.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    private void generateTestData() {
        this.address.setCity("Phuket");
        this.address.setDoor(6);
        this.address.setHouse(6);
        this.address.setStreet("Hell Street 6");
        this.address.setZipCode("666");
        this.address.setId("666");

        this.customer.setAddress(this.address);
        this.customer.setId("1");
        this.customer.setName("John Doe");
        this.customer.setPreference(this.pref);

        this.order.setCustomer(this.customer);
        this.order.setId("order666");
        this.order.setOrderDate(new Date(666));
        this.order.setItems(Arrays.asList(this.item1, this.item2));

        this.item1.setOrder(this.order);
        this.item1.setProductId("item1");
        this.item1.setQuantity(666);
        this.item1.setSingleUnitPrice(BigDecimal.TEN);

        this.item2.setOrder(this.order);
        this.item2.setProductId("item2");
        this.item2.setQuantity(666);
        this.item2.setSingleUnitPrice(new BigDecimal(666));

    }
}
