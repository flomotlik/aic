package at.tuwien.aic666.util;

import org.apache.cxf.binding.soap.SoapFault;

/**
 *
 * @author peter
 */
public class ItemUnavailableFault extends SoapFault {

    public ItemUnavailableFault(final String message) {
        super(message, FAULT_CODE_CLIENT);
    }
}
