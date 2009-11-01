package at.tuwien.aic666.util;

import org.apache.cxf.binding.soap.SoapFault;

/**
 *
 * @author florian
 */
public class UnknownCustomerFault extends SoapFault {

    public UnknownCustomerFault(String message) {
        super(message, FAULT_CODE_CLIENT);
    }
}
