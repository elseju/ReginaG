package Controller;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by kasa2 on 4/17/2016.
 */
public class ReginaConnect {

    private static String sessionID = "xlmt3490";
    private static ReginaConnect regina = null;

    private ReginaConnect(){

    }

    public static ReginaConnect getInstance(){
        if(regina == null){
            regina = new ReginaConnect();
        }
        return regina;
    }

    public String getRequest(String input, String sessionID){
        String url = "https://api.api.ai/v1/query?v=20150910&query=" + input + "&lang=EN&sessionId=" + sessionID;
        String output = null;
        URL obj = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Authorization", "Bearer 2bd47e39f3c9498dac5f366c55d5d868");

            int responseCode = con.getResponseCode();
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            BufferedReader resp = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String res;

            while ((res = resp.readLine()) != null) {
                if(res.contains("\"speech\"")){
                    int location = res.indexOf(":");
                    location += 3;
                    output = res.substring(location, res.length()-2);
                    break;
                }

            }
            resp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
