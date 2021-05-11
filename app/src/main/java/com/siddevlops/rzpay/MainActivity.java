package com.siddevlops.rzpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

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
                //new call_rzpay_fundaccount().execute();
                new call_rzpay_payout().execute();
            }
        });
    }


    /***
     *  Steps for the payout system
     *
     *  1) Create a contact with the id
     *  2) Add a associative bank_account to that id(contact)
     *  3) After adding the bank_account release the pay ( to the fund account)
     */




    /**
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


    public static final String fund_accounts_URL = "https://api.razorpay.com/v1/fund_accounts";
    public void rzpay_fund_account(){

        /**
         * {
         *   "contact_id":"cont_H93PTyy8oRQR5V",
         *   "account_type":"bank_account",
         *   "bank_account":{
         *     "name":"Gaurav Kumar",
         *     "ifsc":"HDFC0000053",
         *     "account_number":"765432123456789"
         *   }
         * }
         */

        JSONObject jsonObject = new JSONObject();
        JSONObject bank_account = new JSONObject();


        try{
            jsonObject.put("contact_id","cont_H94ucoGwgryd2R");
            jsonObject.put("account_type","bank_account");

            bank_account.put("name","Siddharth sharma");
            bank_account.put("ifsc","HDFC0000053");
            bank_account.put("account_number","765432123456789");

            jsonObject.put("bank_account",bank_account);

        }catch (Exception e){
            e.printStackTrace();
        }

        String credened = Credentials.basic("rzp_test_Ua4zZ75Axsxnga","Q07KezIYu1QbHLxUSdBNH8Be");
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        Request request = new Request.Builder().url(fund_accounts_URL).post(body).header("Authorization",credened).build();
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
            Log.i("response->",str);


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static String payout_url = "https://api.razorpay.com/v1/payouts";

    String json;

    public void rzpay_create_payout(){

        /**
         *
             * {
             *   "account_number": "2323230010352249",
             *   "fund_account_id": "fa_H93YFbVGopDSYq",
             *   "amount": 1000000,
             *   "currency": "INR",
             *   "mode": "IMPS",
             *   "purpose": "refund",
             *   "queue_if_low_balance": true,
             *   "reference_id": "Acme Transaction ID 12345",
             *   "narration": "Acme Corp Fund Transfer",
             *   "notes": {
             *     "notes_key_1":"Tea, Earl Grey, Hot",
             *     "notes_key_2":"Tea, Earl Grey… decaf."
             *   }
             * }
         *
         */

        JSONObject jsonObject = new JSONObject();
        JSONObject notes = new JSONObject();
        boolean bal_low = true;

        try {
            jsonObject.put("account_number","2323230010352249");
            jsonObject.put("fund_account_id","fa_H9NvNNnszRyV3S");
            jsonObject.put("amount","10000");
            jsonObject.put("currency","INR");
            jsonObject.put("mode","IMPS");
            jsonObject.put("purpose","refund");
            jsonObject.put("queue_if_low_balance", true);
            jsonObject.put("reference_id","Acme Transaction ID 12345");
            jsonObject.put("narration","Acme Corp Fund Transfer");

            notes.put("notes_key_1","Counsellor Payment");
            notes.put("notes_key_2","Init of Counselor Payment");
            jsonObject.put("notes",notes);


        }catch (Exception e){
            e.printStackTrace();
        }

        String credened = Credentials.basic("rzp_test_Ua4zZ75Axsxnga","Q07KezIYu1QbHLxUSdBNH8Be");
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        Request request = new Request.Builder().url(payout_url).post(body).header("Authorization",credened).build();

        Response response =null;

        try {
            response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            Log.i("response->",str);
           // json = EntityUtils.toString(response.getEntity());
           // json = EntityUtils.toString(response.)

            //JsonParser parser = new JsonParser();
            //JSONObject json = (JSONObject) parser.parse(stringToParse);
            Gson gson = new Gson();
            String rq = gson.toJson(str);
            Log.i("responsestr",rq);
            rq.


        }catch (Exception e){
            e.printStackTrace();
        }



    }





    class call_rzpay_contact extends AsyncTask<Void, Void, Void> {
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


    class call_rzpay_fundaccount extends AsyncTask<Void, Void, Void> {
        private Exception exception;
        protected Void doInBackground(Void... Void) {
            try {
                rzpay_fund_account();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    class call_rzpay_payout extends AsyncTask<Void, Void, Void> {
        private Exception exception;
        protected Void doInBackground(Void... Void) {
            try {
                rzpay_create_payout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


