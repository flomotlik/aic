package at.tuwien.aic666.util;

import org.apache.cxf.binding.soap.SoapFault;

/**
 *
 * @author peter
 */
public class MessageTooLongFault extends SoapFault {

    public MessageTooLongFault(final String message) {
        super(message, FAULT_CODE_CLIENT);
    }
}
