/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
