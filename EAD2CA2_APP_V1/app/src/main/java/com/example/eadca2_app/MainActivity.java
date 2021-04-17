package com.example.eadca2_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private Button button_seller;
    private boolean getButtonClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
                getButtonClicked = true;
            }
        });

        button_seller = (Button) findViewById(R.id.button_seller);
        button_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellerActivity();
            }
        });


    }

    public void openSellerActivity() {
        Intent intent = new Intent(this, SellerActivity.class);
        startActivity(intent);
    }

    private void jsonParse() {
        String url = "https://ead2ca2api.azurewebsites.net/api/Clothes";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            //Loop the Array
                            JSONObject clothe;
                            for (int i = 0; i < response.length(); i++) {
                                clothe = response.getJSONObject(i);
                                int ID = clothe.getInt("id");
                                String Name = clothe.getString("name");
                                double Price = clothe.getInt("price");
                                String brandName = clothe.getString("brandName");
                                String size = clothe.getString("size");
                                String colour = clothe.getString("colour");
                                int SellerID = clothe.getInt("sellerID");
                                if(getButtonClicked=true){
                                    mTextViewResult.clearComposingText();
                                    mTextViewResult.append(String.valueOf(ID) + ", " + Name + ", " + String.valueOf(Price) + ", " + brandName +
                                            ", "  + size + ", "  + colour + ", "  + String.valueOf(SellerID)  + "\n\n");
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