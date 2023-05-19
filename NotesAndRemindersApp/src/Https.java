import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

//For working with Python, not used

public class Https {

    public static final String host = "http://localhost:5000/";

    public static void main(String[] args) throws MalformedURLException{
        HashMap<String, String> body = new HashMap<>();
        body.put("username", "Andrii");
        body.put("password", "FKdas124");
        use("auth/register", body);
    }
    public static void use(String route, HashMap<String, String> requestBody){
        try{
            URL passedUrl = new URL(host+route);
            HttpURLConnection connection = (HttpURLConnection)passedUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            JSONObject jsonRequestObject = new JSONObject(requestBody);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonRequestObject.toString());
            outputStream.flush();
            outputStream.close();
            connection.getResponseMessage();


            BufferedReader inputStr = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuilder strbdr = new StringBuilder();
            while ((str = inputStr.readLine()) != null) {
                strbdr.append(str);
            }
            inputStr.close();
            JSONObject jsonResponseObject = new JSONObject(strbdr.toString());
            System.out.println(jsonResponseObject.getBoolean("success"));
            System.out.println(jsonResponseObject.getString("error_message"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
