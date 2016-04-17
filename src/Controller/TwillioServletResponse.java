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


        TwiMLResponse twiml = new TwiMLResponse();

        //TODO logic for responses goes here
        String url = "https://api.api.ai/v1/query?v=20150910&query=" + body + "&lang=EN&sessionId=" + fromNumber;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer incoming = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            incoming.append(inputLine);
        }
        in.close();

        Message message = new Message(incoming.toString());



        try {
            twiml.append(message);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}