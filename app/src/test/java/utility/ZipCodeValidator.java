package utility;

import org.apache.commons.lang3.StringUtils;
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
    final static String zipcodePattern="\\b[0-9]{6}(?:-[0-9]{5})?\\b";
    private static Pattern pattern;
    private static Matcher matcher;
    static String message="";
    public ZipCodeValidator()
    {
        pattern=Pattern.compile(zipcodePattern);
    }

    public boolean validate (String ZipCode)
    {
        matcher=pattern.matcher(ZipCode);
        return matcher.matches();
    }

    public static String sendRequest(String url)
    {
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

    public static String parseFromJSONResponse(String respo)
    {
        String nameOfPostOffice="";

        try
        {
            JSONObject json = new JSONObject(respo);
            //Parcing data from PostOffice tag in JsonString
            final JSONArray geodata = json.getJSONArray("PostOffice");
            final int n = geodata.length();
            for (int i = 0; i < n; ++i)
            {
                 final JSONObject person = geodata.getJSONObject(i);
                //fetching data from Name tag received through JsonString
                nameOfPostOffice=person.getString("Name");
                //creating comma separated string from Name tag received through JsonString
                message += nameOfPostOffice + ",";
            }
            //removing last comma from separated string
            message=StringUtils.substring(message, 0, message.length() - 1);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //passing list through return
        return message;
    }
}
