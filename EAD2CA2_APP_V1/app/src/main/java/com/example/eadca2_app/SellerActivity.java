package com.example.eadca2_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SellerActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private boolean getButtonClicked = false;
    private FloatingActionButton  backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        mTextViewResult = findViewById(R.id.sellerTextView);
        Button buttonParse = findViewById(R.id.button_show_sellers);
        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
                getButtonClicked = true;
            }
        });

        backButton = (FloatingActionButton ) findViewById(R.id.floatingActionButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void jsonParse() {
        String url = "https://ead2ca2api.azurewebsites.net/api/Sellers";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            //Loop the Array
                            JSONObject seller;
                            for (int i = 0; i < response.length(); i++) {
                                seller = response.getJSONObject(i);
                                int ID = seller.getInt("id");
                                String Name = seller.getString("name");
                                String Location = seller.getString("location");
                                int Rating = seller.getInt("rating");
                                int ClothesSold = seller.getInt("clothesSold");

                                if(getButtonClicked=true){
                                    mTextViewResult.clearComposingText();
                                    mTextViewResult.append(String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Location) + ", " +
                                           String.valueOf(Rating)  + ", "  + String.valueOf(ClothesSold) +"\n\n");
                                }

                            }

//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}