package twiliotestcode; /**
 * Provided by Twilio
 * @version 1.0
 */
// You may want to be more specific in your imports
import java.util.*;
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.twilio.sdk.TwilioRestException;


public class Test {
    // Find your Account Sid and Token at twilio.com/user/account
    private static final String ACCOUNT_SID = "AC33c9de5b0af9acdfdb7e69e33ee7c3ac";
    private static final String AUTH_TOKEN = "c3da874c9c127dcc1c6d6c65ea45616b";

    //tests that the exception works
    //private static final String ACCOUNT_SID = "ACsdaxosdaxosdaxosdaxosdaxosdaxosd";
    //private static final String AUTH_TOKEN = "c3da874c9c127dcc1c6d6c65ea45616b";

    public static void main(String[]args) throws TwilioRestException {
        try{
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            // Build the parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", "7327630159"));
            params.add(new BasicNameValuePair("From", "+13236854799"));
            params.add(new BasicNameValuePair("Body", "TESTING"));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            //System.out.println(message.getSid());
        }catch(TwilioRestException e){
            System.out.println("ERROR\n" + e.getErrorMessage());
        }
    }
}