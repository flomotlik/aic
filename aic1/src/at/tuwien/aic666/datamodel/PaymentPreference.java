/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.datamodel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author peter
 */
@XmlEnum
public enum PaymentPreference {

    /**
     * preference for payments with a credit card.
     */
    @XmlEnumValue("credit-card")
    CREDIT_CARD,

    /**
     * preference for payments with a bank transfer.
     */
    @XmlEnumValue("bank-transfer")
    BANK_TRANSFER
}
