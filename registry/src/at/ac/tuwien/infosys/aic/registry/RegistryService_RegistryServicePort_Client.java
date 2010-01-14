package at.ac.tuwien.infosys.aic.registry;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */
import at.ac.tuwien.infosys.aic.addressing.AttributedURI;
import at.ac.tuwien.infosys.aic.addressing.EndpointReferenceType;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * This class was generated by Apache CXF 2.2.4
 * Thu Jan 14 17:08:07 CET 2010
 * Generated source version: 2.2.4
 * 
 */
public final class RegistryService_RegistryServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://www.infosys.tuwien.ac.at/ait09/registry", "RegistryService");

    private RegistryService_RegistryServicePort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = RegistryService_Service.WSDL_LOCATION;
        if (args.length > 0) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        RegistryService_Service ss = new RegistryService_Service(wsdlURL, SERVICE_NAME);
        RegistryService port = ss.getRegistryServicePort();

        {
            System.out.println("Invoking queryNotificationService...");
            CustomerType type = new CustomerType();
            Random r = new Random();
            int i = 0;
            while (i < 10) {
                type.setId(String.valueOf(r.nextInt()));
                type.setName("Name");
                at.ac.tuwien.infosys.aic.registry.RefType returned = port.queryNotificationService(type);
                JAXBContext context = JAXBContext.newInstance(RefType.class, EndpointReferenceType.class, AttributedURI.class);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.marshal(returned, new File("build/output" + i + ".xml"));
                System.out.println(returned.getEndpointReference().getAddress().getValue());
                i++;
            }
        }
        System.exit(0);
    }
}
