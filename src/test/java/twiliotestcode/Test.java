package test.java.twiliotestcode; /**
 * Provided by Twilio
 * @version 1.0
 */
// You may want to be more specific in your imports
import java.util.*;

import Controller.ReginaConnect;
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.twilio.sdk.TwilioRestException;


public class Test {
    // Find your Account Sid and Token at twilio.com/user/account
    private static final String ACCOUNT_SID = "AC0f16ab120f0ebb95797ed56bda47b875";
    private static final String AUTH_TOKEN = "080ab1bfefb691434cefee9a27a342ef";

    //tests that the exception works
    //private static final String ACCOUNT_SID = "ACsdaxosdaxosdaxosdaxosdaxosdaxosd";
    //private static final String AUTH_TOKEN = "c3da874c9c127dcc1c6d6c65ea45616b";

    public static void main(String[]args) throws TwilioRestException {
        TwilioRestClient rest = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        MessageList list = rest.getAccount().getMessages();

        // Loop over messages and print out a property for each one.
        for (Message message : list) {
            System.out.println(message.getBody());
        }

        ReginaConnect rc = ReginaConnect.getInstance();
        System.out.println(rc.getRequest("hey", "249ujndkfn"));
    }
}