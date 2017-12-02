package com.example.anurag.vistar;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anurag.vistar.Interface.TextSpeechListener;

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
import java.util.HashMap;
import java.util.Locale;

public class CommunicateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TextSpeechListener {
    TextSpeechListener listener;
    static String to, frm, txt;
    static int CODE = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String firstString, secondString;
    //LinearLayout mLayout;
    private TextView txtSpeechInput, txtSpeechOutput;
    private ImageButton btnSpeak, btnSpeak2;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtSpeechOutput = (TextView) findViewById(R.id.txtSpeechOutput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak2 = (ImageButton) findViewById(R.id.btnSpeak2);
        // mLayout = (LinearLayout) findViewById(R.id.headerProgress);
        // hide the action bar
//        getActionBar().hide();

        listener = (TextSpeechListener) this;
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput(1);
            }
        });

        btnSpeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput(2);
            }
        });

        // Language Selection Spinner
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("English");
        categories.add("Hindi");

        ArrayList<String> categories2 = new ArrayList<String>();
        categories2.add("Hindi");
        categories2.add("English");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput(int promptId) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        Log.d("karma", "id is " + promptId);
        CODE = promptId;
        intent.putExtra("id1", promptId);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

//                    int id = data.getIntExtra("id1",1);
                    int id = CODE;
                    Log.d("karma", "id is " + id);

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("karma", "speech input is ohk... " + result.get(0));
                    txtSpeechInput.setText(result.get(0));
                    String response = "";
                    // call api
                    if (id == 1) {
                        Log.d("karma", "first and sec string is " + firstString + "   " + secondString);
                        response = fetchData(result.get(0), firstString, secondString);
                    } else if (id == 2) {
                        Log.d("karma", "first and sec string is " + firstString + "   " + secondString);
                        response = fetchData(result.get(0), secondString, firstString);
                    }


                    txtSpeechInput.setText(response);


                }
                break;
            }

        }
    }

    String fetchData(String data, String firstString, String secondString) {
        //  mLayout.setVisibility(View.VISIBLE);

        to=secondString;
        frm=firstString;
        txt=data;

        String url = MyApplication.SPEECH_LINK + "?to=" + secondString + "&from=" + firstString + "&text="+data+"";
//        url += data;
        Log.d("karma", "url is " + url);
        MyTask task = new MyTask();
        task.execute(url);

        return null;
    }


    // Create GetText Metod
    public String GetText() throws UnsupportedEncodingException {

        String text = null;
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL(MyApplication.SPEECH_LINK);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //conn.setRequestProperty("Ocp-Apim-Subscription-Key", BuildConfig.API_KEY);
            //conn.setRequestProperty("Content-Type", "application/json");


            //Create JSONObject here

//            SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//            final String latitude = prefs.getString("currentLat", null);
//            final String longitude=prefs.getString("currentLong",null);

            // Create data variable for sent values to server

            String data = URLEncoder.encode("to", "UTF-8")
                    + "=" + URLEncoder.encode(to, "UTF-8");
            data += "&" + URLEncoder.encode("from", "UTF-8") + "="
                    + URLEncoder.encode(frm, "UTF-8");
            data += "&" + URLEncoder.encode("text", "UTF-8") + "="
                    + URLEncoder.encode(txt, "UTF-8");


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


    public String getData(String u) {
        String result = null;
        HttpURLConnection conn = null;
        InputStream stream = null;
        URL url = null;
        try {
            Log.d("click", "inside try url is " + u);

            url = new URL(u);
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
                    Log.d("click", "data is " + result);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinner) {
            String s = adapterView.getSelectedItem().toString();
            if (s.equals("Hindi")) {
                firstString = "hi";
            } else {
                firstString = "en";
            }

        }
        if (adapterView.getId() == R.id.spinner2) {
            String s = adapterView.getSelectedItem().toString();
            if (s.equals("Hindi")) {
                secondString = "hi";
            } else {
                secondString = "en";
            }
        }
// On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }



    public class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... str) {
            String d = null;
            try {
                d = GetText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return d;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //mLayout.setVisibility(View.GONE);
            txtSpeechOutput.setText(s);
            Log.d("karma","omnpost execute");
            listener.onListLoaded(s);


        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onListLoaded(String s) {
        if(CODE==1){
            //englidh to hindi
            Log.d("karma","bool de bhai");
            loadSpeakingLanguages(s);
            textToSpeech.setLanguage(Locale.forLanguageTag("hin"));


        }
        else if (CODE==2){
            //hindi to english
            loadSpeakingLanguages(s);
        }

    }




    private void loadSpeakingLanguages(String textToTranslate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(textToTranslate);
        } else {
            ttsUnder20(textToTranslate);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }


    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        super.onResume();
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        super.onPause();
    }


}
