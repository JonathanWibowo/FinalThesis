package com.thesis.megahjaya.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.GudangActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PenjualanActivity extends AppCompatActivity {

    // For navigation bar
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    // For recycler view
    private RecyclerView recyclerView;

    // For search view
    private SearchView searchMaterial;

    private TextView displayName;
    private Button scan, lanjut;
    private final int GET_BARCODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        // Display navigation bar
        navigationBar();

        searchMaterial = (SearchView) findViewById(R.id.penjualanSearchView);
        recyclerView = (RecyclerView) findViewById(R.id.penjualanRecyclerView);
        scan = (Button) findViewById(R.id.scanButton);
        lanjut = (Button) findViewById(R.id.nextButton);
        displayName = (TextView) findViewById(R.id.displayName);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PenjualanActivity.this, ScanActivity.class), GET_BARCODE);
            }
        });
    }

    public void navigationBar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.penjualanDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.penjualanNavigationMenu);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle("Penjualan"); // Give title to current navigation bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.penjualanMenu:
                        Intent penjualan = new Intent(getApplicationContext(), PenjualanActivity.class);
                        startActivity(penjualan);
                        break;

//                    case R.id.historiPenjualanMenu:
//                        Intent historiPenjualan = new Intent(getApplicationContext(), HistoriPenjualanActivity.class);
//                        startActivity(historiPenjualan);
//                        break;

                    case R.id.gudangMenu:
                        Intent gudang = new Intent(getApplicationContext(), GudangActivity.class);
                        startActivity(gudang);
                        break;
                }

                return false;
            }
        });
    }

    // Make the sidebar menu icon clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getMaterialData(String getBarcodeData){
        // Request material data using material code to URL
        String url = "";
        try {
            url = "https://thesisandroid.000webhostapp.com/material/getMaterial.php?itemCode=" + URLEncoder.encode(getBarcodeData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        // Loading message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil barang...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("material");

                            for (int x = 0; x < jsonArray.length(); x++){
                                JSONObject insideArray = jsonArray.getJSONObject(x);

                                String getMaterialName = insideArray.getString("name");
                                String getMaterialCode = insideArray.getString("itemCode");
                                String getMaterialDesc = insideArray.getString("desc");
                                String getMaterialGroup = insideArray.getString("group");
                                int getMaterialQuantity = insideArray.getInt("quantity");
                                int getMaterialPrice = insideArray.getInt("price");

                                displayName.append(getMaterialName + ", " + getMaterialCode + ", " + getMaterialDesc + ", " + getMaterialGroup + ", " + getMaterialQuantity + ", " + getMaterialPrice);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PenjualanActivity.this, error.toString() /*Barang tidak ditemukan*/, Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    // Get barcode data & pass it to function for get the material using captured data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_BARCODE && resultCode == RESULT_OK){
            String getBarcodeData = data.getStringExtra("scannedBarcode");
            getMaterialData(getBarcodeData);
        }
    }
}
