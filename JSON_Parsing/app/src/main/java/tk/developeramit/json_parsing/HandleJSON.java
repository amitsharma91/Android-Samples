package tk.developeramit.json_parsing;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by developeramit on 3/1/18.
 */

public class HandleJSON {
    /**
     * For accepting URL and return JSON to String FORMAT
     *
     * @param urlString
     * @return JSON to String
     */
    public String getJSONFromUrl(String urlString) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {

            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName() + "-ERROR: ", "" + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(this.getClass().getName() + "-ERROR: ", "" + e);
            }
            return stringBuilder.toString();
        }
    }
}