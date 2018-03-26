package com.thesis.erpmegahjaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.erpmegahjaya.singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final int GET_BARCODE = 0;
    private TextView displayItemName;
    private Button scanBtn, data;
    private String barcodeData; // For store item code from barcode
//    private static final String url = "https://thesisandroid.000webhostapp.com/material/listItem.php";
    private static final String url = "http://192.168.100.2/thesis_test/listMaterialInventory/material.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayItemName = (TextView) findViewById(R.id.materialName);
        scanBtn = (Button) findViewById(R.id.barcodeScanner);
        data = (Button) findViewById(R.id.getData);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ScanActivity.class), GET_BARCODE);

                parseJSON();
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseJSON();
            }
        });
    }

    private void parseJSON(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itemCode", barcodeData);

        JSONObject jsonObject = new JSONObject(hashMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHECK", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("material");

                            for(int x = 0; x < jsonArray.length(); x++){
                                JSONObject material = jsonArray.getJSONObject(x);

                                String getName = material.getString("name");
                                String getItemCode = material.getString("itemCode");
                                String getDesc = material.getString("desc");

                                if (barcodeData == getItemCode){
                                    displayItemName.append(getName + ", " + getItemCode + ", " + getDesc + "\n");
                                }
                                else{
                                    String getFail = material.getString("fail");
                                    Toast.makeText(MainActivity.this, getFail, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
//        requestQueue.add(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    // Get data from barcode scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_BARCODE && resultCode == RESULT_OK){
            String getBarcodeData = data.getStringExtra("scannedBarcode");
            barcodeData = getBarcodeData;

//            displayItemName.setText(barcodeData);
        }
    }
}
