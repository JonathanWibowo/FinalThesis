package com.thesis.megahjaya.Gudang.AddNewMaterial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.LoginActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddNewMaterialActivity extends AppCompatActivity {

    private EditText newName, newGroup, newCode, newDesc, newQuantity, newMeasurement, newPrice, newMinimum;
    private Button addNew;

    private final String url = "https://thesisandroid.000webhostapp.com/material/addMaterial.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_material);

        getSupportActionBar().setTitle("Tambah Barang Baru"); // Give title to current navigation bar

        newName = (EditText) findViewById(R.id.addNewMaterialName);
        newGroup = (EditText) findViewById(R.id.addNewMaterialGroup);
        newCode = (EditText) findViewById(R.id.addNewMaterialCode);
//        newDesc = (EditText) findViewById(R.id.addNewMaterialDescription);
        newQuantity = (EditText) findViewById(R.id.addNewMaterialQuantity);
        newMeasurement = (EditText) findViewById(R.id.addNewMaterialUnit);
        newPrice = (EditText) findViewById(R.id.addNewMaterialPrice);
        newMinimum = (EditText) findViewById(R.id.addNewMaterialMinimum);
        addNew = (Button) findViewById(R.id.addNewMaterialButton);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if all of the edittext is empty or no
//                if(isAllFieldFilled(new EditText[]{newName, newGroup, newCode, newQuantity, newMeasurement, newPrice, newMinimum})){
                Log.i("beforeClick", "beforeClickDone");
                addNewMaterial();
//                }
//                else{
//                    Toast.makeText(AddNewMaterialActivity.this, "Semua kolom harus diisi", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    private boolean isAllFieldFilled(EditText[] editTexts){
        for(int x = 0; x < editTexts.length; x++){
            EditText listFields = editTexts[x];

            if(listFields.getText().toString().isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void addNewMaterial(){
        String name, group, code, measurement;
        int quantity, price, minimum;

        Log.i("first", "done");

        // Tidy up everything
        name = newName.getText().toString();
        group = newGroup.getText().toString();
        code = newCode.getText().toString();
        quantity = Integer.parseInt(newQuantity.getText().toString());
//        measurement = newMeasurement.getText().toString();
        price = Integer.parseInt(newPrice.getText().toString());
//        minimum = Integer.parseInt(newMinimum.getText().toString());

        // Get data from user input
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("item_name", name);
//        hashMap.put("item_type", group);
//        hashMap.put("personal_code", code);
//        hashMap.put("item_quantity", String.valueOf(quantity));
//        hashMap.put("item_measurement", measurement);
//        hashMap.put("item_price", String.valueOf(price));
//        hashMap.put("item_minimum", String.valueOf(minimum));

        hashMap.put("item_name", name);
        hashMap.put("item_type", group);
        hashMap.put("personal_code", code);
        hashMap.put("item_quantity", String.valueOf(quantity));
//        hashMap.put("item_measurement", measurement);
        hashMap.put("item_price", String.valueOf(price));
//        hashMap.put("item_minimum", String.valueOf(minimum));

        Log.i("second", "done");

        JSONObject jsonObject = new JSONObject(hashMap);

        Log.i("third", "done");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getResponseCode = response.getInt("status");
                            Log.i("getCode", String.valueOf(getResponseCode));

                            if(getResponseCode == 201){
                                startActivity(new Intent(AddNewMaterialActivity.this, SuccessAddNewActivity.class));

//                                Intent intent = new Intent(AddNewMaterialActivity.this, SuccessAddNewActivity.class);
//                                intent.putExtra("item_name", newName.getText().toString());
//                                intent.putExtra("item_code", newCode.getText().toString());
//                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get forbidden response
                if(error.networkResponse != null && error.networkResponse.statusCode == 400){
                    startActivity(new Intent(AddNewMaterialActivity.this, LoginActivity.class));
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
