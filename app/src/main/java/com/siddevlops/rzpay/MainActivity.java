package com.siddevlops.rzpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnhome;

    private String credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnhome = findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"asd",Toast.LENGTH_SHORT).show();
                new call_aws().execute();
            }
        });
    }



    /**
     *
     *
     *
         * {
         *     "name":"Siddharth",
         *     "email":"Siddharth@sid.com",
         *     "contact":"9123456789",
         *     "type":"employee",
         *     "reference_id":"Acme Contact ID 12345"
         * }
         *
     */



    public static final String BASE_URL = "https://api.razorpay.com/v1/contacts";
    public void rzpay_create_contact(){


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name","sidearms");
            jsonObject.put("email","siddharth2@sid.com");
            jsonObject.put("contact","489464618694");
            jsonObject.put("type","employee");
            jsonObject.put("reference_id","siddharth2");

        }catch (Exception e){
            e.printStackTrace();
        }

        String credened = Credentials.basic("rzp_test_Ua4zZ75Axsxnga","Q07KezIYu1QbHLxUSdBNH8Be");

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        Request request = new Request.Builder().url(BASE_URL).post(body).header("Authorization",credened).build();

        //Request authenticatedRequest = request.newBuilder().header("Authorization", credentials).build();


        Response response =null;

        try {
            response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            Log.i("response ->",str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    class call_aws extends AsyncTask<Void, Void, Void> {
        private Exception exception;
        protected Void doInBackground(Void... Void) {
            try {
                rzpay_create_contact();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


