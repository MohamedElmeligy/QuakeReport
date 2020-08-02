package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String SIMPLE_LOG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static ArrayList<Info> fetchEarthquakeData(String StringUrl){
        //create url
        URL url = makeUrl(StringUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHTTPRequest(url);
        }catch (Exception e){
            Log.e(SIMPLE_LOG , "Error closing InputSream: " , e);
        }

        ArrayList<Info> earthquake = extractEarthquakesFromJSON(jsonResponse);

        return earthquake;
    }

    /**
     * Return a list of {@link Info} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Info> extractEarthquakesFromJSON(String earthquake_json) {

        if (TextUtils.isEmpty(earthquake_json)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Info> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            //reading the JSON Object
            JSONObject root = new JSONObject(earthquake_json);
            //finding the array called "features"
            JSONArray features = root.getJSONArray("features");

            for(int x = 0; x < features.length() ; x++ ){
                //getting the no.x object from the array
                JSONObject earthQuake = features.getJSONObject(x);
                JSONObject properties = earthQuake.getJSONObject("properties");
                String magnitude = properties.getString("mag");
                String place = properties.getString("place");
                String url = properties.getString("url");
                long time = properties.getLong("time");

                earthquakes.add(new Info(magnitude,place,time,url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(SIMPLE_LOG, "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFrom(inputStream);
            }else {
                Log.e(SIMPLE_LOG , "Error respond Code: " + urlConnection.getResponseCode() );
            }

        }catch (Exception e){
            Log.e(SIMPLE_LOG , "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static URL makeUrl(String StringUrl){
        URL url = null;
        //parsing the string url
        try{
            url = new URL(StringUrl);
        }catch (Exception e){
            Log.e(SIMPLE_LOG , "Error with Creating URL" , e);
        }

        return url;
    }

    private static String readFrom(InputStream in) throws IOException{
        StringBuilder output = new StringBuilder();
        if(in != null){
            InputStreamReader inputStreamReader = new InputStreamReader(in , Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

}
