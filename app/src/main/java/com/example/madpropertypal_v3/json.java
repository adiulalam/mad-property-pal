package com.example.madpropertypal_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;

public class json extends AppCompatActivity {

    WebView browser;

    DB_Controller mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        browser = (WebView) findViewById(R.id.webkit);
        mydb = new DB_Controller(this);
        ArrayList<Data> theList = new ArrayList<Data>();
        Cursor data = mydb.getListContents();

        if (data.moveToFirst()) {
            do {
                Data data2 = new Data();
                data2.name = data.getString(1);

                theList.add(data2);

            } while (data.moveToNext());


            Gson gson = new Gson();
            String jsonInString = gson.toJson(theList);



            data.close();


            try {
                URL pageURL = new URL("http://gillwindallapp1.appspot.com/madservlet");
                HttpURLConnection con =
                        (HttpURLConnection) pageURL.openConnection();

                String part1 = ("{\"userId\":\"aa6932u\",\"detailList\":");
                String part2= jsonInString+"}";

                Toast.makeText(json.this, part1+part2, Toast.LENGTH_LONG).show();

                String jsonString = (part1+part2);
                JsonThread myTask = new JsonThread(this, con, jsonString);
                Thread t1 = new Thread(myTask, "JSON Thread");
                t1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public class Data {
        public String name;
    }


    class JsonThread implements Runnable {
        private AppCompatActivity activity;
        private HttpURLConnection con;
        private String jsonPayLoad;

        public JsonThread(AppCompatActivity activity,
                          HttpURLConnection con, String jsonPayload) {
            this.activity = activity;
            this.con = con;
            this.jsonPayLoad = jsonPayload;
        }


        @Override
        public void run() {
            String response = "";
            if (prepareConnection()) {
                response = postJson();
            } else {
                response = "Error preparing the connection";
            }
            showResult(response);
        }

        private boolean prepareConnection() {
            try {
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                return true;
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            return false;
        }

        @SuppressLint("NewApi")
        private String readStream(InputStream in) {
            StringBuilder sb = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in))) {
                String nextLine = "";
                while ((nextLine = reader.readLine()) != null) {
                    sb.append(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }


        private String postJson() {
            String response = "";
            try {
                String postParameters = "jsonpayload="
                        + URLEncoder.encode(jsonPayLoad, "UTF-8");
                con.setFixedLengthStreamingMode(postParameters.getBytes().length);
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(postParameters);
                out.close();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    response = readStream(con.getInputStream());
                } else {
                    response = "Error contacting server: " + responseCode;
                }
            } catch (Exception e) {
                response = "Error executing code";
            }
            return response;
        }

        private void showResult(final String response) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String page = generatePage(response);
                    ((json) activity).browser.loadData(page,
                            "text/html", "UTF-8");
                }
            });
        }

        private String generatePage(String content) {
            return "<html><body><p>" + content + "</p></body></html>";
        }
    }


}