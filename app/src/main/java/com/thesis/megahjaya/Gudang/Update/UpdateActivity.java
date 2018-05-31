package com.thesis.megahjaya.Gudang.Update;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.Detail.DetailInventoryActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    private final static String url = "https://thesisandroid.000webhostapp.com/material/updateMaterial.php";

    private Toolbar toolbar;
    private EditText name, code, measurement, group, quantity, minimum, price;
    private Button lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        toolbar = (Toolbar) findViewById(R.id.updateToolbar);
        name = (EditText) findViewById(R.id.updateMaterialName);
        code = (EditText) findViewById(R.id.updateMaterialCode);
        measurement = (EditText) findViewById(R.id.updateMaterialMeasurement);
        group = (EditText) findViewById(R.id.updateMaterialGroup);
        quantity = (EditText) findViewById(R.id.updateMaterialQuantity);
        minimum = (EditText) findViewById(R.id.updateMaterialMinimumStock);
        price = (EditText) findViewById(R.id.updateMaterialPrice);
        lanjut = (Button) findViewById(R.id.updateBtn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("detailMaterialName")); // Give title to current navigation bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set name to make it cannot be changed
        name.setEnabled(false);

        name.setText(getIntent().getStringExtra("detailMaterialName"));
        code.setText(getIntent().getStringExtra("detailMaterialCode"));
        measurement.setText(getIntent().getStringExtra("detailMaterialMeasurement"));
        group.setText(getIntent().getStringExtra("detailMaterialGroup"));
        quantity.setText(getIntent().getStringExtra("detailMaterialQuantity"));
        minimum.setText(getIntent().getStringExtra("detailMaterialMinimum"));
        price.setText(getIntent().getStringExtra("detailMaterialPrice"));

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();

//                startActivity(new Intent(UpdateActivity.this, DetailInventoryActivity.class));
            }
        });
    }

    private void updateData(){
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", name.getText().toString());
        hashMap.put("itemCode", code.getText().toString());
        hashMap.put("measurement", measurement.getText().toString());
        hashMap.put("group", group.getText().toString());
        hashMap.put("quantity", quantity.getText().toString());
        hashMap.put("minimum", minimum.getText().toString());
        hashMap.put("price", price.getText().toString());

        JSONObject jsonObject = new JSONObject(hashMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getResponseCode = response.getInt("status");

                            if(getResponseCode == 200){
                                Log.i("getResponseCode", String.valueOf(getResponseCode));
                                updateResult(hashMap);
//                                startActivity(new Intent(UpdateActivity.this, DetailInventoryActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get internal server error result
                if(error.networkResponse != null && error.networkResponse.statusCode == 500){
                    Toast.makeText(UpdateActivity.this, "Error, terjadi kesalahan di server", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void updateResult(HashMap<String, String> hashMap){
        Intent intent = new Intent(UpdateActivity.this, DetailInventoryActivity.class);
        intent.putExtra("updateResult", hashMap);
        setResult(RESULT_OK);
        finish();
    }
}
