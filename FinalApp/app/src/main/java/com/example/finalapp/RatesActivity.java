package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RatesActivity extends AppCompatActivity {

    private RequestQueue queue;
    //REST API URL
    String url = "https://api.exchangerate.host/latest";

    //we'll use these variables for intial rates values and to update parsed values in TextViews
    private String updateTime = "";
    private double rateUSD = 0, rateGBP = 0, rateRUR = 0, rateSEK = 0, rateNOK = 0, rateJPY = 0;



    //method to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        //Checking if we have any data stored previously (e.g. after changing orientation
        if (savedInstanceState == null) {
            //method to update rates from REST API
            updRate();
        }
    }


    //method to update rates from REST API
    public void updRate() {

        //Instantiate the request queue
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
        {
            //we'll use a separate method to parse data from JSON object in the response from REST API
            parseJsonAndUpdateData (response);

        }, error -> {   //error handling
                Toast.makeText (this, "Error!", Toast.LENGTH_LONG).show();
            }
        );

        queue.add(stringRequest);

    }


    //method to parse data from JSON object
    private void parseJsonAndUpdateData(String response) {

        //parsing from JSON
        try {
            JSONObject rates = new JSONObject(response);
            rateUSD = rates.getJSONObject("rates").getDouble("USD");
            rateGBP = rates.getJSONObject("rates").getDouble("GBP");
            rateRUR = rates.getJSONObject("rates").getDouble("RUB");
            rateSEK = rates.getJSONObject("rates").getDouble("SEK");
            rateNOK = rates.getJSONObject("rates").getDouble("NOK");
            rateJPY = rates.getJSONObject("rates").getDouble("JPY");

        }
        //handing errors
        catch (JSONException e){
            e.printStackTrace();
        }

        //updating values in TextViews
        TextView ratesUSDTextView = findViewById(R.id.ratesUSDTextView);
        TextView ratesGBPTextView = findViewById(R.id.ratesGBPTextView);
        TextView ratesRURTextView = findViewById(R.id.ratesRURTextView);
        TextView ratesSEKTextView = findViewById(R.id.ratesSEKTextView);
        TextView ratesNOKTextView = findViewById(R.id.ratesNOKTextView);
        TextView ratesJPYTextView = findViewById(R.id.ratesJPYTextView);
        TextView updatedTimeTextView = findViewById(R.id.updatedTimeTextView);
        ratesUSDTextView.setText("" + rateUSD);
        ratesGBPTextView.setText("" + rateGBP);
        ratesRURTextView.setText("" + rateRUR);
        ratesSEKTextView.setText("" + rateSEK);
        ratesNOKTextView.setText("" + rateNOK);
        ratesJPYTextView.setText("" + rateJPY);

        //updating the current date and time of request
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        updateTime = "Updated: "+dateFormat.format(cal.getTime());
        updatedTimeTextView.setText(updateTime);

    }

    //method used to update Currency Exchange Rates manually on clicking the button by the user
    public void updRateButton(View view) {
        updRate();
    }


    //method to save data - needed in case of re-creating the activity, e.g. in case of changing orientation
    @Override
    protected void onSaveInstanceState( Bundle savedInstanceState ) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("USD", rateUSD);
        savedInstanceState.putDouble("GBP", rateGBP);
        savedInstanceState.putDouble("RUR", rateRUR);
        savedInstanceState.putDouble("SEK", rateSEK);
        savedInstanceState.putDouble("NOK", rateNOK);
        savedInstanceState.putDouble("JPY", rateJPY);
        savedInstanceState.putString("TIME", updateTime);

    }

    //method to restore data - needed in case of re-creating the activity, e.g. in case of changing orientation
    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);
        rateUSD = savedInstanceState.getDouble("USD");
        rateGBP = savedInstanceState.getDouble("GBP");
        rateRUR = savedInstanceState.getDouble("RUR");
        rateSEK = savedInstanceState.getDouble("SEK");
        rateNOK = savedInstanceState.getDouble("NOK");
        rateJPY = savedInstanceState.getDouble("JPY");
        updateTime = savedInstanceState.getString("TIME");

        TextView ratesUSDTextView = findViewById(R.id.ratesUSDTextView);
        TextView ratesGBPTextView = findViewById(R.id.ratesGBPTextView);
        TextView ratesRURTextView = findViewById(R.id.ratesRURTextView);
        TextView ratesSEKTextView = findViewById(R.id.ratesSEKTextView);
        TextView ratesNOKTextView = findViewById(R.id.ratesNOKTextView);
        TextView ratesJPYTextView = findViewById(R.id.ratesJPYTextView);
        TextView updatedTimeTextView = findViewById(R.id.updatedTimeTextView);

        ratesUSDTextView.setText("" + rateUSD);
        ratesGBPTextView.setText("" + rateGBP);
        ratesRURTextView.setText("" + rateRUR);
        ratesSEKTextView.setText("" + rateSEK);
        ratesNOKTextView.setText("" + rateNOK);
        ratesJPYTextView.setText("" + rateJPY);
        updatedTimeTextView.setText("" + updateTime);

    }



}