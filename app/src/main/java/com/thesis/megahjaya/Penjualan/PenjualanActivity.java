package com.thesis.megahjaya.Penjualan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.GudangActivity;
import com.thesis.megahjaya.Histori_Penjualan.HistoriPenjualanActivity;
import com.thesis.megahjaya.LoginActivity;
import com.thesis.megahjaya.Penjualan.Adapter.PenjualanAdapter;
import com.thesis.megahjaya.Penjualan.Search.SearchActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PenjualanActivity extends AppCompatActivity {

    // Session
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // For navigation bar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For recycler view
    private RecyclerView recyclerView;
    private PenjualanAdapter penjualanAdapter;

    // For list item (view data)
    private ArrayList<MaterialPenjualan> materialPenjualanArrayList;

    // For list penjualanTemp customer (save data)
    private PenjualanTemp penjualanTemp;
    private ArrayList<PenjualanTemp> penjualanTempArrayList = new ArrayList<>();

    // For getting value back (item code) which will be use for getting material
    private final int GET_BARCODE = 0;
    private final int GET_MATERIAL_CODE = 1;

    // For calculate total unit price
    private Integer materialQuantity;
    private Integer materialPrice;
    private Integer totalMaterialUnit;


    int materialQuantityDatabase; // For quantity from database
    ArrayList<String> materialCodeDatabase = new ArrayList<>(); // For store material code

    private Toolbar toolbar;
    private Button scan, lanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        toolbar = (Toolbar) findViewById(R.id.penjualanToolbar);
        scan = (Button) findViewById(R.id.scanButton);
        lanjut = (Button) findViewById(R.id.nextButton);

        // Get session data (token) from login
        getSessionData();

        // Manage recyclerview
        manageRecyclerView();

        // Display navigation bar
        navigationBar();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PenjualanActivity.this, ScanActivity.class), GET_BARCODE);
            }
        });

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getAdapter().getItemCount() == 0){
                    Toast.makeText(PenjualanActivity.this, "Isi barang terlebih dahulu", Toast.LENGTH_LONG).show();
                }
                else {
                    processData();
                }
            }
        });
    }

    // Get session data
    public void getSessionData(){
        // get token session
        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        String userToken = sharedPreferences.getString("token", "");

        Log.i("GET_SESSION", String.valueOf(sharedPreferences));
        Log.i("TOKEN", String.valueOf(userToken));

        if(userToken.isEmpty()){
            startActivity(new Intent(PenjualanActivity.this, LoginActivity.class));
        }
    }

    // manage recyclerview
    private void manageRecyclerView(){
        LinearLayoutManager linearLayoutManagerPenjualan = new LinearLayoutManager(this);

        // set the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.penjualanRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManagerPenjualan);
        materialPenjualanArrayList = new ArrayList<>();

        // Set the adapter
        penjualanAdapter = new PenjualanAdapter(materialPenjualanArrayList, PenjualanActivity.this);
        recyclerView.setAdapter(penjualanAdapter);
    }

    // Set search view on toolbar & make search function
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_search, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PenjualanActivity.this, SearchActivity.class));
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ArrayList<MaterialPenjualan> getListPenjualanName = new ArrayList<>();
//
//                for(MaterialPenjualan listPenjualan : materialPenjualanArrayList){
//                    String getMaterialNamePenjualan = listPenjualan.getName().toLowerCase();
//
//                    if(getMaterialNamePenjualan.contains(newText)){
//                        getListPenjualanName.add(listPenjualan);
//                    }
//                }
//
//                penjualanAdapter.setFilterPenjualan(getListPenjualanName);
//
//                return true;
//            }
//        });

//        return super.onCreateOptionsMenu(menu);
//    }

    // Generate navigation bar
    public void navigationBar(){
        setSupportActionBar(toolbar);

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
                        finish();
                        startActivity(getIntent());
                        break;

                    case R.id.historiPenjualanMenu:
                        Intent historiPenjualan = new Intent(PenjualanActivity.this, HistoriPenjualanActivity.class);
                        startActivity(historiPenjualan);
                        break;

                    case R.id.gudangMenu:
                        Intent gudang = new Intent(PenjualanActivity.this, GudangActivity.class);
                        startActivity(gudang);
                        break;

                    case R.id.logoutMenu:
                        Intent logout = new Intent(PenjualanActivity.this, LoginActivity.class);
                        startActivity(logout);
                        break;
                }

                return false;
            }
        });
    }

    // Create menu on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Make the sidebar menu icon clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // For search icon
        switch(item.getItemId()){
            case R.id.searchIcon:
                startActivity(new Intent(PenjualanActivity.this, SearchActivity.class));
//                startActivityForResult(new Intent(PenjualanActivity.this, SearchActivity.class), GET_MATERIAL_CODE);
                break;
        }

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Get material data from database based from scanned code
    private void getMaterialData(String getBarcodeData){
        // Request material data using material code to URL
        String url = "";
        try {
            String urlMaterialCode = URLEncoder.encode(getBarcodeData, "UTF-8");
//            materialCodeDatabase.add(urlMaterialCode.toString());
            url = "https://thesisandroid.000webhostapp.com/material/getMaterial.php?itemCode=" + urlMaterialCode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        // Loading message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil barang...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Integer getResponseCode = response.getInt("status");

                            // get OK status
                            if(getResponseCode == 200) {
                                JSONArray jsonArray = response.getJSONArray("material");

                                for (int x = 0; x < jsonArray.length(); x++) {
                                    JSONObject insideArray = jsonArray.getJSONObject(x);

                                    // Get the JSON data & add the data to list
                                    MaterialPenjualan listPenjualan = new MaterialPenjualan(
                                            insideArray.getString("name"),
                                            insideArray.getString("itemCode"),
                                            insideArray.getString("group"),
                                            insideArray.getInt("quantity"),
                                            insideArray.getInt("price")
                                    );

                                    // check if the material code already exist
//                                    for(int n = 0; n < materialCodeDatabase.size(); n++){
//                                        if(materialCodeDatabase.contains(listPenjualan.getCode())){
//                                            Toast.makeText(PenjualanActivity.this, "Barang sudah diambil", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
                                    materialPenjualanArrayList.add(listPenjualan);
                                    Log.i("TEST", materialPenjualanArrayList.toString());
                                }
                                penjualanAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get forbidden result
                if(error.networkResponse != null && error.networkResponse.statusCode == 403){
                    startActivity(new Intent(PenjualanActivity.this, LoginActivity.class));
                }
                // get not found result
                if(error.networkResponse != null && error.networkResponse.statusCode == 404){
                    progressDialog.dismiss();
                    Toast.makeText(PenjualanActivity.this, "Barang tidak ditemukan", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
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

    // save the list data to new array list and send to next activity using parcelable
    private void processData(){
        // get list of viewholder
        for(int y = 0; y < recyclerView.getChildCount(); y++){
            PenjualanAdapter.ViewHolder viewHolder = (PenjualanAdapter.ViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(y));

            // set and calculate total material unit
            materialQuantity = Integer.parseInt(viewHolder.jumlahBarangPenjualan.getText().toString());
            materialPrice = Integer.parseInt(viewHolder.hargaBarangPenjualan.getText().toString());
            totalMaterialUnit = materialQuantity * materialPrice;

            // get all data & move to another array list for parcel
            penjualanTemp = new PenjualanTemp(
                    viewHolder.namaBarangPenjualan.getText().toString(),
                    Integer.parseInt(viewHolder.jumlahBarangPenjualan.getText().toString()),
                    Integer.parseInt(viewHolder.hargaBarangPenjualan.getText().toString()),
                    totalMaterialUnit
            );

            penjualanTempArrayList.add(penjualanTemp);
        }

        // Send array list to penjualanTemp success page using parcelable
        Intent sendListMaterialIntent = new Intent(PenjualanActivity.this, PenjualanInfoActivity.class);
        sendListMaterialIntent.putParcelableArrayListExtra("listMaterial", penjualanTempArrayList);
        startActivity(sendListMaterialIntent);
    }
}
