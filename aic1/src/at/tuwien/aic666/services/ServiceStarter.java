package at.tuwien.aic666.services;

import at.tuwien.aic666.util.ServerPasswordCallback;
import java.util.HashMap;
import java.util.Map;
import javax.xml.ws.Endpoint;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 *
 * @author florian
 * @author kevin
 */
public class ServiceStarter {

    /**
     *  Server-endpoint according to specification.
     */
    public static final String smsAddress = "http://localhost:8088/smsservice";
    public static final String mailAddress = "http://localhost:8089/mailservice";
    public static final String orderManagementAddress = "http://localhost:8083/orderservice";
    public static final String bankingAddress = "http://localhost:8084/bankingservice";
    public static final String customerAddress = "http://localhost:8085/";// "/customers" is set in the service-bean
    public static final String customerSoapAddress = "http://localhost:8086/customersoap";
    
    public static void main(String args[]) {
        // Parse command line parameter indicating service security properties
        boolean secure = true;
        if (args.length == 1 && args[0].equals("nonsecure")) {
            secure = false;
            System.out.println("Starting services without security..");
        } else if (args.length != 0) {
            System.out.println("Usage: java ServiceStarter [nonsecure]");
            System.exit(1);
        } else {
            System.out.println("Starting services..");
        }

        Endpoint.publish(smsAddress, new SMSService());
        Endpoint.publish(mailAddress, new MailService());
        Endpoint.publish(orderManagementAddress, new OrderManagementService());
        Endpoint.publish(customerSoapAddress, new CustomerManagementServiceSoap());

        // Usage of ServerFactoryBean necessary to set authentication interceptors
        JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
        serverFactory.setServiceClass(BankingService.class);
        serverFactory.setServiceBean(new BankingService());
        serverFactory.setAddress(bankingAddress);
        if (secure) {
            // Security properties for BankingService
            Map<String,Object> inProps= new HashMap<String,Object>();
            inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
            inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
            // inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
            // Callback used to retrieve password for given user.
            inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
            WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
            serverFactory.getInInterceptors().add(wssIn);
            serverFactory.getInInterceptors().add(new LoggingInInterceptor());
        }
        serverFactory.create();

        // RS customer-service
        JAXRSServerFactoryBean rs = new JAXRSServerFactoryBean();
        rs.setServiceBeans(new CustomerManagementService());
//        rs.setBindingId(JAXRS)
        rs.setAddress(customerAddress);
        rs.create();

        System.out.println("All services running..");

        JaxWsProxyFactoryBean svrFactory = new JaxWsProxyFactoryBean();
        svrFactory.setServiceClass(IOrderManagementService.class);
        svrFactory.setAddress(ServiceStarter.orderManagementAddress);
        IOrderManagementService service = (IOrderManagementService) svrFactory.create();

        service.insertTestData();
        System.out.println("Test data inserted..");
    }
}
