package Controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class TwillioServletResponse extends HttpServlet {
    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        String serverName = request.getServerName();
        String fromNumber = request.getParameter("From");
        String body = request.getParameter("Body");

        System.out.println("Server " + serverName);
        System.out.println("fromNumber " + fromNumber);
        System.out.println("body " + body);

        Logger.getLogger (TwillioServletResponse.class.getName()).log(Level.INFO, "Server " + serverName);
        Logger.getLogger (TwillioServletResponse.class.getName()).log(Level.INFO, "fromNumber " + fromNumber);
        Logger.getLogger (TwillioServletResponse.class.getName()).log(Level.INFO, "body " + body);
    }
}