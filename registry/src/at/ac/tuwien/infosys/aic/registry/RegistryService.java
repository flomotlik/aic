package at.ac.tuwien.infosys.aic.registry;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.4
 * Thu Jan 14 17:08:07 CET 2010
 * Generated source version: 2.2.4
 * 
 */
 
@WebService(targetNamespace = "http://www.infosys.tuwien.ac.at/ait09/registry", name = "RegistryService")
@XmlSeeAlso({ObjectFactory.class,ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RegistryService {

    @WebResult(name = "Reference", targetNamespace = "http://www.infosys.tuwien.ac.at/ait09/registry", partName = "response")
    @WebMethod(operationName = "query_notification_service")
    public RefType queryNotificationService(
        @WebParam(partName = "parameters", name = "Customer", targetNamespace = "http://www.infosys.tuwien.ac.at/ait09/registry")
        CustomerType parameters
    );
}
