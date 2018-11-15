package pw.pbdiary.maeari.blog;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class useAPIData {
    public String request(String _url, ContentValues _params){
        
        HttpURLConnection APIConnector = null;
        StringBuffer apiParams = new StringBuffer();


        if (_params == null) {
            apiParams.append("");
        } else { 
            boolean more_params = false;
            
            String key;
            String value;

            for(Map.Entry<String, Object> parameter : _params.valueSet()){
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (more_params) {
                    apiParams.append("&");
                } else if(!more_params && _params.size() >= 2) { 
                    more_params = true;
                }
                apiParams.append(key).append("=").append(value);

            }
        }

        try{
            URL url = new URL(_url);
            APIConnector = (HttpURLConnection) url.openConnection();

            APIConnector.setRequestMethod("GET");
            APIConnector.setRequestProperty("Accept-Charset", "UTF-8");
            APIConnector.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

            String strParams = apiParams.toString();
            OutputStream os = APIConnector.getOutputStream();
            os.write(strParams.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            if (APIConnector.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(APIConnector.getInputStream(),StandardCharsets.UTF_8));

            String line;
            String sum_of_line = "";

            while ((line = reader.readLine()) != null){
                sum_of_line += line;
            }

            return sum_of_line;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (APIConnector != null)
                APIConnector.disconnect();
        }

        return null;

    }
}
