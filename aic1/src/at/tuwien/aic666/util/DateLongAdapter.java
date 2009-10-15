/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.tuwien.aic666.util;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * Adapts the xml long value to a date object and vice versa.
 * @author peter
 */
public class DateLongAdapter extends XmlAdapter<Long, Date> {

    @Override
    public Date unmarshal(final Long v) {
        return new Date(v);
    }

    @Override
    public Long marshal(final Date v) {
        return v.getTime();
    }

   

}
