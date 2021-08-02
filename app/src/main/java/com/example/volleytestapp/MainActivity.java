package com.example.volleytestapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    RequestQueue mRequestQueue;
//    RequestQueue mRequestQueueArray;

    RequestQueue mSingleRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRequestQueue = Volley.newRequestQueue(this);
//        mRequestQueueArray = Volley.newRequestQueue(this);

        mSingleRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://official-joke-api.appspot.com/random_joke", null,
                response -> {

                    Log.i("VALIDRESPONSE", response + "");

                    try {

                        int jokId = response.getInt("id");
                        Toast.makeText(getApplicationContext(),jokId + "", Toast.LENGTH_SHORT).show();

                        String setup = response.getString("setup");
                        Toast.makeText(getApplicationContext(),setup ,Toast.LENGTH_LONG).show();

                    }catch (JSONException e){

                        Toast.makeText(getApplicationContext(),"you internet not working",Toast.LENGTH_SHORT).show();
                    }

                }, error -> Log.e("RESPONSE ERROR",error.getMessage()));

        mSingleRequestQueue.add(jsonObjectRequest);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://official-joke-api.appspot.com/random_ten", null, response -> {

                    Log.i("ARRAYRESPONSE", response + "");

                    for (int index= 0; index < response.length(); index++){

                        try {
                            JSONObject jokeJasonObject = response.getJSONObject(index);
                            Log.i("RESULTING",jokeJasonObject.getString("setup" + " - "
                                    + jokeJasonObject.getString("punchline")));

                        }catch (JSONException e){

                            e.printStackTrace();
                        }
                    }

                }, error -> Log.e("ARRAYERROR", error.getMessage()));


        mSingleRequestQueue.add(jsonArrayRequest);
    }
}