package com.thesis.erpmegahjaya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class PenjualanActivity extends AppCompatActivity {

    // For navigation bar
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    // For recycler view
    private RecyclerView recyclerView;

    private TextView displayName;
    private Button scanBtn, data, btn;
    private final int GET_BARCODE = 0;
//    private static final String url = "http://192.168.100.2/thesis_test/listMaterial/getItem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        navigationBar();

        recyclerView = findViewById(R.id.penjualanRecyclerView);
        displayName = (TextView) findViewById(R.id.nameText);
        scanBtn = (Button) findViewById(R.id.barcodeScanner);
        data = (Button) findViewById(R.id.getData);
        btn = (Button) findViewById(R.id.button);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PenjualanActivity.this, ScanActivity.class), GET_BARCODE);
            }
        });

//        data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                parseJSON();
//            }
//        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PenjualanActivity.this, PenjualanInfoActivity.class));
            }
        });
    }

    public void navigationBar(){
        drawerLayout = findViewById(R.id.penjualanLayout);
        navigationView = findViewById(R.id.navPenjualan);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle("Penjualan"); // Give title to current navigation bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id){
                    case R.id.penjualanMenu:
                        Intent penjualan = new Intent(getApplicationContext(), PenjualanActivity.class);
                        startActivity(penjualan);
                        break;

                    case R.id.historiPenjualanMenu:
                        Intent historiPenjualan = new Intent(getApplicationContext(), HistoriPenjualanActivity.class);
                        startActivity(historiPenjualan);
                        break;

                    case R.id.gudangMenu:
                        Intent gudang = new Intent(getApplicationContext(), GudangActivity.class);
                        startActivity(gudang);
                        break;

                    case R.id.barangBaruMenu:
//                        Intent barangBaru = new Intent(PenjualanActivity.this, );
//                        startActivity(barangBaru);
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseJSON(String getBarcodeData){
        String url = "";
        try {
            url = "https://thesisandroid.000webhostapp.com/material/getItem.php?itemCode=" + URLEncoder.encode(getBarcodeData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Kasih notification
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHECK", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("material");

                            for (int x = 0; x < jsonArray.length(); x++){
                                JSONObject insideArray = jsonArray.getJSONObject(x);

                                String getMaterialName = insideArray.getString("name");
                                String getMaterialCode = insideArray.getString("itemCode");
                                int getMaterialQuantity = insideArray.getInt("quantity");
                                int getMaterialPrice = insideArray.getInt("price");

                                displayName.append(getMaterialName + ", " + getMaterialCode + ", " + getMaterialQuantity + ", " + getMaterialPrice);
                            }
//                            for(int x = 0; x < jsonArray.length(); x++){
//                                JSONObject material = jsonArray.getJSONObject(x);
//
//                                String getName = material.getString("name");
//                                String getItemCode = material.getString("itemCode");
//                                String getDesc = material.getString("desc");
//
//                                if (getBarcodeData.equals(getItemCode)){
//                                    displayItemName.append(getName + ", " + getItemCode + ", " + getDesc + "\n");
//                                }
//                                else{
//                                    String getFail = material.getString("fail");
//                                    Toast.makeText(PenjualanActivity.this, getFail, Toast.LENGTH_SHORT).show();
//                                }
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("QWERTYUIOP LMAO LMAO", error.toString());
                Toast.makeText(PenjualanActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    // Get data from barcode scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_BARCODE && resultCode == RESULT_OK){
            String getBarcodeData = data.getStringExtra("scannedBarcode");
//            barcodeData = getBarcodeData;

            parseJSON(getBarcodeData);
//            displayItemName.setText(barcodeData);
        }
    }
}