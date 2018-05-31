package com.thesis.megahjaya.Gudang.AddNewMaterial;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.thesis.megahjaya.Gudang.GudangActivity;
import com.thesis.megahjaya.LoginActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddNewMaterialActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText newName, newGroup, newCode, newQuantity, newMeasurement, newPrice, newMinimum;
    private Button addNew;

    private final String url = "https://thesisandroid.000webhostapp.com/material/addMaterial.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_material);

        toolbar = (Toolbar) findViewById(R.id.gudangToolbar);
        newName = (EditText) findViewById(R.id.addNewMaterialName);
        newGroup = (EditText) findViewById(R.id.addNewMaterialGroup);
        newCode = (EditText) findViewById(R.id.addNewMaterialCode);
        newQuantity = (EditText) findViewById(R.id.addNewMaterialQuantity);
        newMeasurement = (EditText) findViewById(R.id.addNewMaterialUnit);
        newPrice = (EditText) findViewById(R.id.addNewMaterialPrice);
        newMinimum = (EditText) findViewById(R.id.addNewMaterialMinimum);
        addNew = (Button) findViewById(R.id.addNewMaterialButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Barang Baru"); // Give title to current navigation bar

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if all of the edittext is empty or no
                if(isAllFieldFilled(new EditText[]{newName, newGroup, newCode, newQuantity, newMeasurement, newPrice, newMinimum})){
                    addNewMaterial();
                }
                else{
                    Toast.makeText(AddNewMaterialActivity.this, "Semua kolom harus diisi", Toast.LENGTH_LONG).show();
                }
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
        // Get data from user input
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", newName.getText().toString());
        hashMap.put("itemCode", newCode.getText().toString());
        hashMap.put("group", newGroup.getText().toString());
        hashMap.put("measurement", newMeasurement.getText().toString());
        hashMap.put("quantity", String.valueOf(Integer.parseInt(newQuantity.getText().toString())));
        hashMap.put("minimum", String.valueOf(Integer.parseInt(newMinimum.getText().toString())));
        hashMap.put("price", String.valueOf(Integer.parseInt(newPrice.getText().toString())));

        JSONObject jsonObject = new JSONObject(hashMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response", String.valueOf(response));
                        try {
                            Integer getResponseCode = response.getInt("status");

                            if(getResponseCode == 201){
                                Intent returnSuccess = new Intent(AddNewMaterialActivity.this, GudangActivity.class);
                                setResult(RESULT_OK, returnSuccess);
                                startActivity(returnSuccess);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", String.valueOf(error));
                // get forbidden response
                if(error.networkResponse != null && error.networkResponse.statusCode == 403){
                    startActivity(new Intent(AddNewMaterialActivity.this, LoginActivity.class));
                }
                // get internal server error response
                if(error.networkResponse != null && error.networkResponse.statusCode == 500){
                    serverErrorMessage();
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void serverErrorMessage(){
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddNewMaterialActivity.this);
        alertDialogBuilder.setMessage("Terjadi masalah pada server")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }
        );

        // Create alert dialog message
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Server Error");
        alertDialog.show();
    }
}
