package utility;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipCodeValidator {


    //note in this expression below, each back slash is an escape character, so
    //the regular expression should be "\b[0-9]{5}(?:-[0-9]{4})?\b"
    final static String zipcodePattern="\\b[0-9]{5}(?:-[0-9]{4})?\\b";
    private static Pattern pattern;
    private static Matcher matcher;
    public ZipCodeValidator()
    {
        pattern=Pattern.compile(zipcodePattern);
    }

    public boolean validate(String ZipCode)
    {
        matcher=pattern.matcher(ZipCode);
        return matcher.matches();
    }

    public static String sendRequest(String url) {
        String result = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpParams httpParameters = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            HttpConnectionParams.setTcpNoDelay(httpParameters, true);
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            InputStream ips = response.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String s;
            while (true) {
                s = buf.readLine();
                if (s == null || s.length() == 0)
                    break;
                sb.append(s);
            }
            buf.close();
            ips.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void parseFromJSONResponse(String respo)
    {

        try
        {
            JSONObject myjson = new JSONObject(respo);
            JSONArray arr = myjson.getJSONArray("PostOffice");
            for (int i = 0; i < arr.length(); i++)
            {
                String post_id = arr.getJSONObject(i).getString("Name");
                System.out.println(post_id);

            }
            
            //JSONObject jsonObj1 = myjson.getJSONObject("PostOffice");
            //JSONArray jsonObj2 = jsonObj1.getJSONArray("PostOffice");
            //JSONObject jsonObj3 = jsonObj2.getJSONObject(0);
           // System.out.println(jsonObj3.getJSONObject("PostOffice"));
            //System.out.println("here ===>>>"+jsonObj3.getJSONObject("PostOffice").get("Name").toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


