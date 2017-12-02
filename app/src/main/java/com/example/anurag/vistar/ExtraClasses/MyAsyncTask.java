package com.example.anurag.vistar.ExtraClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.anurag.vistar.Actvities.CustomBusinessObject;
import com.example.anurag.vistar.Interface.ListLoadedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by anurag on 12/2/2017.
 */

public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    ListLoadedListener listener;
    ProgressDialog progressDialog;
    Context context;
    String urlString;
    public MyAsyncTask(Context context,String url){
        this.context=context;
        listener = (ListLoadedListener) context;
        urlString=url;
    }

    public static final String MY_PREFS_NAME="myPref";

    // Create GetText Metod
    public String GetText() throws UnsupportedEncodingException {

        String text = null;
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("http://192.168.137.1/disAPI/submit.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //conn.setRequestProperty("Ocp-Apim-Subscription-Key", BuildConfig.API_KEY);
            //conn.setRequestProperty("Content-Type", "application/json");


            //Create JSONObject here

            SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            final String latitude = prefs.getString("currentLat", null);
            final String longitude=prefs.getString("currentLong",null);

            // Create data variable for sent values to server

            String data = URLEncoder.encode("lat", "UTF-8")
                    + "=" + URLEncoder.encode(latitude, "UTF-8");
            data += "&" + URLEncoder.encode("lon", "UTF-8") + "="
                    + URLEncoder.encode(longitude, "UTF-8");



            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            //wr.write(jsonParam.toString());
            wr.flush();
            Log.d("karma", "data is " + data);

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            Log.d("karma ", "response is " + text);
        } catch (Exception ex) {
            Log.d("karma", "exception at last " + ex);
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

return text;
    }



    public String getData() {
        String result = null;
        HttpURLConnection conn = null;
        InputStream stream = null;
        URL url = null;
        try {
            Log.d("click", "inside try");

            url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            Log.d("click", "inside try 2");
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 200) {

                stream = conn.getInputStream();
                Log.d("click", "inside try 3");
                if (stream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();
                    Log.d("click", "inside tr4y");
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {

                        builder.append(line + "\n");
                        Log.d("line", line);

                    }

                    result = builder.toString();
                    Log.d("click","data is "+result);
                }
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }


    @Override
    protected String doInBackground(Void... voids) {
       String data=null;
//        try {
//            Log.d("karma", "called");
//            data=getData();
//            Log.d("karma", "after called");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        data=getData();

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            Log.d("click", "inside on post execute" + "the resulted string is" + s);
            listener.onListLoaded(fetchAndPutInList(s));
            //listener.onListLoaded(new ArrayList<CustomBusinessObject>());
            //Log.d("click", "inside on post execute" + "the resulted string is" + s);
        }

    }

    public ArrayList<CustomBusinessObject> fetchAndPutInList(String s) {
        ArrayList<CustomBusinessObject> list = new ArrayList<>();
        if (s != null) {

            try {


                //JSONObject rootObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {

                    int businessId = 0;
                    String businessName = "Vistar";
                    String date = "00-00-0000";
                   // String url = "http://newshq.io";
                    String imageUrl = "http://i0.wp.com/newshq.io/wp-content/uploads/2016/07/bottom-image.jpg?fit=1024%2C838";
                    String businessDescription = "It seems there is no content available for this post.";
                    String lat="0";
                    String lon="0";
                    String availability="0";
                    String avg_rating="0";
                    String businessType="universal";
                    int businessDistance=0;

                    //JSONObject categoryObject = null;


                    JSONObject currentBussObj = jsonArray.getJSONObject(i);

                    if (currentBussObj.has("business_id") && !currentBussObj.isNull("business_name")) {
                        businessId=currentBussObj.optInt("business_id");

                    }
                    if (currentBussObj.has("business_name") && !currentBussObj.isNull("business_name")) {
                        businessName = currentBussObj.optString("business_name");
                    }
                    if (currentBussObj.has("business_description") && !currentBussObj.isNull("business_description")) {
                        businessDescription = currentBussObj.optString("business_description");
                    }

                    if (currentBussObj.has("lat") && !currentBussObj.isNull("lat")) {
                        lat=currentBussObj.getString("lat");
                    }
                    if (currentBussObj.has("lon") && !currentBussObj.isNull("lon")) {
                        lon=currentBussObj.getString("lon");
                    }

                    if (currentBussObj.has("total_avalibility") && !currentBussObj.isNull("total_avalibility")) {
                        availability= currentBussObj.optString("total_avalibility");

                    }

                    if (currentBussObj.has("avg_rating") && !currentBussObj.isNull("avg_rating")) {
                        avg_rating = currentBussObj.optString("avg_rating");

                    }

                    if (currentBussObj.has("type") && !currentBussObj.isNull("type")) {
                        businessType = currentBussObj.optString("type");

                    }
                    if (currentBussObj.has("image_url") && !currentBussObj.isNull("image_url")) {
                        Log.d("karma","image id is "+currentBussObj.optString("image_url"));
                        imageUrl = currentBussObj.optString("image_url");

                    }

                    if (currentBussObj.has("dist_from_user") && !currentBussObj.isNull("dist_from_user")) {
                        businessDistance = currentBussObj.optInt("dist_from_user");

                    }



                    CustomBusinessObject current = new CustomBusinessObject();
                    current.setName(businessName);
                    current.setBusinessId(businessId);
                    current.setCategory(businessType);
                    current.setDiscription(businessDescription);
                    current.setDistance(businessDistance);
                    current.setImgUrl(imageUrl);
                    current.setLat(lat);
                    current.setLon(lon);
                    current.setRating(avg_rating);
                    current.setTotalAailibility(availability);
                    list.add(current);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;
    }


}
