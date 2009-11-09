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
        // TODO depending on the value of pc.getIdentifier() set the password to something which will be compared to what the client sent
        pc.setPassword("dummy");
    }

}
