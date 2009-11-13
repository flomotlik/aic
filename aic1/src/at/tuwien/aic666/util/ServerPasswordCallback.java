package at.tuwien.aic666.util;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

/**
 *
 * @author kevin
 */
public class ServerPasswordCallback implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        System.out.println("Testing authentication for username " + pc.getIdentifier());
        // ideally we would want to set this depending on the value of pc.getIdentifier()
        pc.setPassword("test-password");
    }

}