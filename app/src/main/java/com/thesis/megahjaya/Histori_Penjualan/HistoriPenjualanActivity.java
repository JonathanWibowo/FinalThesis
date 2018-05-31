package com.thesis.megahjaya.Histori_Penjualan;

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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.GudangActivity;
import com.thesis.megahjaya.Histori_Penjualan.Adapter.HistoriPenjualanAdapter;
import com.thesis.megahjaya.LoginActivity;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoriPenjualanActivity extends AppCompatActivity {

    // For navigation bar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For recycler view
    private RecyclerView recyclerView;
    private HistoriPenjualanAdapter historiPenjualanAdapter;

    // For list data (invoice)
    private ArrayList<Invoice> invoiceArrayList;

    private static final String url = "https://thesisandroid.000webhostapp.com/invoice/listInvoice.php";

    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_penjualan);

        toolbar = (Toolbar) findViewById(R.id.historiPenjualanToolbar);
        searchView = (SearchView) findViewById(R.id.historiPenjualanSearchView);

        // Manage recycler view
        manageRecyclerView();

        // Display navigation bar
        navigationBar();

        // Search function
        search();

        // Display invoice data
        displayInvoice();
    }

    public void navigationBar(){
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.historiPenjualanDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.historiPenjualanNavigationMenu);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle("Histori Penjualan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.penjualanMenu:
                        Intent penjualan = new Intent(HistoriPenjualanActivity.this, PenjualanActivity.class);
                        startActivity(penjualan);
                        break;

                    case R.id.historiPenjualanMenu:
                        finish();
                        startActivity(getIntent());
                        break;

                    case R.id.gudangMenu:
                        Intent gudang = new Intent(HistoriPenjualanActivity.this, GudangActivity.class);
                        startActivity(gudang);
                        break;

                    case R.id.logoutMenu:
                        Intent logout = new Intent(HistoriPenjualanActivity.this, LoginActivity.class);
                        startActivity(logout);
                        break;
                }

                return false;
            }
        });
    }

    // Make the sidebar menu icon clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void manageRecyclerView(){
        LinearLayoutManager linearLayoutManagerHistoriPenjualan = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.historiPenjualanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManagerHistoriPenjualan);
        invoiceArrayList = new ArrayList<>();
    }

    private void search(){

    }

    private void displayInvoice(){
        // Display the loading message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil bon...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getResponseCode = response.getInt("status");

                            if(getResponseCode == 200){
                                JSONArray jsonArray = response.getJSONArray("invoice");

                                for(int x = 0; x < jsonArray.length(); x++){
                                    JSONObject jsonObject = new JSONObject();

                                    // Get the JSON data & add the data to list
                                    Invoice invoice = new Invoice(
                                            jsonObject.getInt("invoiceType"),
                                            jsonObject.getString("invoiceNumber"),
                                            jsonObject.getString("invoiceName"),
                                            jsonObject.getString("invoiceAddress"),
                                            jsonObject.getString("invoiceInfo"),
                                            jsonObject.getInt("invoiceTotalPrice")
                                    );
                                    invoiceArrayList.add(invoice);
                                }

                                // Set the adapter
                                historiPenjualanAdapter = new HistoriPenjualanAdapter(invoiceArrayList, getApplicationContext());
                                recyclerView.setAdapter(historiPenjualanAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
