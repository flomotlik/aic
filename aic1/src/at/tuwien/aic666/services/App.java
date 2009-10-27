package at.tuwien.aic666.services;

import javax.xml.ws.Endpoint;

/**
 *
 * @author florian
 */
public class App {

    public static void main(String args[]) {
        System.out.println("Starting Server");
        SMSService smsService = new SMSService();
        MailService mailService = new MailService();
        String smsAddress = "http://localhost:9000/smsService";
        String mailAddress = "http://localhost:9000/mailService";
        Endpoint.publish(smsAddress, smsService);
        Endpoint.publish(mailAddress, mailService);
    }
}
