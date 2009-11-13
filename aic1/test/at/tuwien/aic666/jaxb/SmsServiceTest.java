/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.tuwien.aic666.jaxb;

import at.tuwien.aic666.datamodel.Customer;
import at.tuwien.aic666.services.INotify;
import at.tuwien.aic666.services.ServiceStarter;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @author florian
 */
public class SmsServiceTest {

    public static void main(String args[]) {
        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(INotify.class);
        svrFactory.setAddress(ServiceStarter.smsAddress);
        INotify service = (INotify) svrFactory.create();
        service.notifyCustomer(new Customer("1"), "Message");
    }
}
