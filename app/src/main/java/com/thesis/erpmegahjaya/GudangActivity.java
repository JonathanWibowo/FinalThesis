package com.thesis.erpmegahjaya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GudangActivity extends AppCompatActivity {

    // For navigation bar
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    // For recycler view
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    // For list of the item
    private List<listMaterialInventory> listMaterialInventories;

    private TextView displayAllMaterial;
    private static final String url = "https://thesisandroid.000webhostapp.com/material/listItem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudang);

        // Display navigation bar
        navigationBar();

        displayAllMaterial = (TextView) findViewById(R.id.allMaterial);

        recyclerView = (RecyclerView) findViewById(R.id.gudangRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMaterialInventories = new ArrayList<>();

        // display 25 item
        for(int x = 0; x < 25; x++){
            listMaterialInventory listInventory = new listMaterialInventory("Nama Barang", "Kode Barang", 10, 10000);
            listMaterialInventories.add(listInventory);
        }

        adapter = new InventoryAdapter(listMaterialInventories, this);
        recyclerView.setAdapter(adapter);

        // Display whole material
        material();
    }

    public void navigationBar(){
        drawerLayout = findViewById(R.id.gudangLayout);
        navigationView = findViewById(R.id.navGudang);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle("Gudang"); // Give title to current navigation bar
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

    private void material(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("material");

                    for (int x = 0; x < jsonArray.length(); x++){
                        JSONObject jsonObject = jsonArray.getJSONObject(x);

                        String getMaterialName = jsonObject.getString("name");
                        String getMaterialCode = jsonObject.getString("itemCode");
                        int getMaterialQuantity = jsonObject.getInt("quantity");
                        int getMaterialPrice = jsonObject.getInt("price");

                        displayAllMaterial.append(getMaterialName + ", " + getMaterialCode + ", " + getMaterialQuantity + ", " + getMaterialPrice + "\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GudangActivity.this, "Tidak ada barang", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
