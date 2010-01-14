
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package at.ac.tuwien.infosys.aic.registry;

import java.util.logging.Logger;
import javax.xml.namespace.QName;
import at.ac.tuwien.infosys.aic.addressing.AttributedQName;
import at.ac.tuwien.infosys.aic.addressing.AttributedURI;
import at.ac.tuwien.infosys.aic.addressing.EndpointReferenceType;
import at.ac.tuwien.infosys.aic.addressing.ServiceNameType;

/**
 * This class was generated by Apache CXF 2.2.4
 * Thu Jan 14 17:08:07 CET 2010
 * Generated source version: 2.2.4
 * 
 */

@javax.jws.WebService(
                      serviceName = "RegistryService",
                      portName = "RegistryServicePort",
                      targetNamespace = "http://www.infosys.tuwien.ac.at/ait09/registry",
                      endpointInterface = "at.ac.tuwien.infosys.aic.registry.RegistryService")
                      
public class RegistryServiceImpl implements RegistryService {

    private static final Logger LOG = Logger.getLogger(RegistryServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see at.ac.tuwien.infosys.ait09.registry.RegistryService#queryNotificationService(at.ac.tuwien.infosys.ait09.registry.CustomerType  parameters )*
     */
    public at.ac.tuwien.infosys.aic.registry.RefType queryNotificationService(CustomerType customer) {
        LOG.info("Executing operation queryNotificationService");
        System.out.println("Id: " + customer.id + " Name: " + customer.name);
        try {
            int hc = customer.getName().hashCode() + customer.getId().hashCode();
            RefType toReturn = null;
            if ((hc % 2) == 0) {
                toReturn = createReference("http://localhost:8088/smsservice", "NotificationPT", "NotificationPT", "NitificationService");
            } else {
                toReturn = createReference("http://localhost:8088/mailservice", "NotificationPT", "NotificationPT", "NitificationService");
            }
            return toReturn;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private RefType createReference(String address, String portType, String portName, String serviceName) {
        RefType toReturn = new RefType();
        EndpointReferenceType ef = new EndpointReferenceType();
        AttributedURI uri = new AttributedURI();
        uri.setValue(address);
        ef.setAddress(uri);
        AttributedQName qname = new AttributedQName();
        qname.setValue(new QName(portType));
        ef.setPortType(qname);
        ServiceNameType service = new ServiceNameType();
        service.setPortName(portName);
        service.setValue(new QName(serviceName));
        ef.setServiceName(service);
        toReturn.setEndpointReference(ef);
        return toReturn;
    }

}
