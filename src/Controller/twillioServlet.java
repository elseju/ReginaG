package Controller;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
 
import java.util.ArrayList;
import java.util.List;

public class twillioServlet {

	 // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC8abf2b3598450d06e001142ea2512484";
    public static final String AUTH_TOKEN = "f5b91031ff8109a0535ae72d6ee737e4";

    public void sendMessage() throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "5595732440"));
        params.add(new BasicNameValuePair("From", "+16674015610"));
        params.add(new BasicNameValuePair("Body", "TESTING"));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        
        //System.out.println(message.getSid());
    }
	
}
