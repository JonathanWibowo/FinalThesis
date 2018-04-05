package com.thesis.erpmegahjaya;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.erpmegahjaya.adapter.InventoryAdapter;
import com.thesis.erpmegahjaya.singleton.MySingleton;

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
    private InventoryAdapter inventoryAdapter;

    // For list of the item
    private List<listMaterialInventory> listMaterialInventories;

    private SearchView searchMaterial;
    private static final String url = "https://thesisandroid.000webhostapp.com/material/listMaterial.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudang);

        searchMaterial = (SearchView) findViewById(R.id.gudangSearchView);
        recyclerView = (RecyclerView) findViewById(R.id.gudangRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listMaterialInventories = new ArrayList<>();

        // Display navigation bar
        navigationBar();

        // Search View function
        search();

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
                switch(item.getItemId()){
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

//                    case R.id.addMaterialQuantity:
//                        Toast.makeText(GudangActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.removeMaterial:
//                        Toast.makeText(GudangActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                        break;
                }

                return false;
            }
        });
    }

    // Make the menu appear
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.inventory_menu, menu);

        return true;
    }

    // Make the sidebar menu icon clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.addMaterialQuantity:
//                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.removeMaterial:
//                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
//                break;
//        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void search(){
        searchMaterial.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                List<listMaterialInventory> getListInventoryName = new ArrayList<>();

                for(listMaterialInventory listInventory : listMaterialInventories){
                    String getMaterialName = listInventory.getName().toLowerCase();

                    if(getMaterialName.contains(string)){
                        getListInventoryName.add(listInventory);
                    }
                }

                inventoryAdapter.setFilter(getListInventoryName);

                return true;
            }
        });
    }

    private void material(){
        // Display the loading message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil barang...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Remove the loading message when the loading done
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("material");

                            for (int x = 0; x < jsonArray.length(); x++){
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                // Get the JSON data & add the data to list
                                listMaterialInventory listInventory = new listMaterialInventory(
                                        jsonObject.getString("name"),
                                        jsonObject.getString("itemCode"),
                                        jsonObject.getString("desc"),
                                        jsonObject.getInt("quantity"),
                                        jsonObject.getInt("price")
                                );
                                listMaterialInventories.add(listInventory);
                            }

                            // Set the adapter
                            inventoryAdapter = new InventoryAdapter(listMaterialInventories, getApplicationContext());
                            recyclerView.setAdapter(inventoryAdapter);

                        } catch (JSONException e) {
                            Log.i("ERROR", e.toString());
                            e.printStackTrace();
                        }
                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Remove the loading message when the loading done
                progressDialog.dismiss();
                Toast.makeText(GudangActivity.this, "Tidak ada barang", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
