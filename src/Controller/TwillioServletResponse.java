package Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class TwillioServletResponse extends HttpServlet {
 
    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sessionId = request.getRequestedSessionId();
        String fromNumber = request.getParameter("From");
        String body = request.getParameter("Body");

        System.out.println("SessionID " + sessionId);
        System.out.println("fromNumber " + fromNumber);
        System.out.println("body " + body);

        String incoming = ReginaConnect.getInstance().getRequest(body,fromNumber);

        Message message = new Message(incoming);

        TwiMLResponse twiml = new TwiMLResponse();
        try {
            twiml.append(message);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}