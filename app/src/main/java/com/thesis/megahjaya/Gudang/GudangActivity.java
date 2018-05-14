package com.thesis.megahjaya.Gudang;

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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.Adapter.InventoryAdapter;
import com.thesis.megahjaya.Gudang.AddNewMaterial.AddNewMaterialActivity;
import com.thesis.megahjaya.Gudang.AddNewMaterial.SuccessAddNewActivity;
import com.thesis.megahjaya.Histori_Penjualan.HistoriPenjualanActivity;
import com.thesis.megahjaya.LoginActivity;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.pagination.prevNextPagination;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GudangActivity extends AppCompatActivity {

    // For navigation bar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For recycler view
    private RecyclerView recyclerView;
    private InventoryAdapter inventoryAdapter;

    // For list of the item
    private ArrayList<ListMaterialInventory> listMaterialInventories;

    // For pagination
    prevNextPagination prevNextPaginationInventory = new prevNextPagination();
    private int currentPage = 0;
    private int totalPage = prevNextPaginationInventory.TOTAL_MATERIAL/prevNextPaginationInventory.LIST_MATERIAL_PER_PAGE;

//    public int TOTAL_MATERIAL;
//    public static final int LIST_MATERIAL_PER_PAGE = 25;
//    public int REMAINING_MATERIAL = TOTAL_MATERIAL % LIST_MATERIAL_PER_PAGE;
//    public int LAST_PAGE = TOTAL_MATERIAL / LIST_MATERIAL_PER_PAGE;
    private Button prev,next;

    // For search item
    private SearchView searchMaterial;

    private static final String url = "https://thesisandroid.000webhostapp.com/material/listMaterial.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudang);

        // Pagination
        prev = (Button) findViewById(R.id.previousBtn);
        next = (Button) findViewById(R.id.nextBtn);
        searchMaterial = (SearchView) findViewById(R.id.gudangSearchView);
//        prev.setEnabled(false); // make the button cannot be click because user will visit the first pagination for inventory page

        recyclerView = (RecyclerView) findViewById(R.id.gudangRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerInventory = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManagerInventory);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listMaterialInventories = new ArrayList<>();

        // Pagination infinity scroll
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if(isLastMaterial(recyclerView)){
//                    Log.i("CHECK_PAGINATION", String.valueOf("SUCCESS"));
//                }
//            }
//        });


        // Display navigation bar
        navigationBar();

        // Search View function
        search();

        // Display whole material
        displayMaterial();

        // Set the click listener for pagination
//        paginationButton();
    }

    // check for the last material
//    private boolean isLastMaterial(RecyclerView recyclerView) {
//        if(recyclerView.getAdapter().getItemCount() != 0){
//            int lastMaterial = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//
//            if(lastMaterial != recyclerView.NO_POSITION && lastMaterial == recyclerView.getAdapter().getItemCount() - 1){
//                return true;
//            }
//        }
//
//        return false;
//    }

    public void navigationBar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.gudangDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.gudangNavigationMenu);

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
        // For the inventory menu (not the navigation bar menu)
        switch (item.getItemId()){
            case R.id.addNewMaterial:
                Intent addNewMaterialIntent = new Intent(getApplicationContext(), AddNewMaterialActivity.class);
                startActivity(addNewMaterialIntent);
                break;
            case R.id.addMaterialQuantity:
                Intent intent = new Intent(getApplicationContext(), SuccessAddNewActivity.class);
                startActivity(intent);
                break;
        }

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
                ArrayList<ListMaterialInventory> getListInventoryName = new ArrayList<>();

                for(ListMaterialInventory listInventory : listMaterialInventories){
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

    private void displayMaterial(){
        // Display the loading message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil barang...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Remove the loading message when the loading done
                        progressDialog.dismiss();
                        try {
                            Integer getResponseCode = response.getInt("status");

                            if(getResponseCode == 200) {
                                JSONArray jsonArray = response.getJSONArray("material");

                                for (int x = 0; x < jsonArray.length(); x++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(x);

                                    // Get the JSON data & add the data to list
                                    ListMaterialInventory listInventory = new ListMaterialInventory(
                                            jsonObject.getString("name"),
                                            jsonObject.getString("itemCode"),
                                            jsonObject.getString("desc"),
                                            jsonObject.getString("group"),
                                            jsonObject.getInt("quantity"),
                                            jsonObject.getInt("price")
                                    );
                                    listMaterialInventories.add(listInventory);
                                }

                                // Set the adapter
                                inventoryAdapter = new InventoryAdapter(listMaterialInventories, getApplicationContext());
                                recyclerView.setAdapter(inventoryAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Remove the loading message when the loading done
                progressDialog.dismiss();

                // get forbidden result
                if(error.networkResponse != null && error.networkResponse.statusCode == 400) {
                    startActivity(new Intent(GudangActivity.this, LoginActivity.class));
                }
                // get not found status
                if(error.networkResponse != null && error.networkResponse.statusCode == 404){
                    Toast.makeText(GudangActivity.this, "Tidak ada barang", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

//    public void paginationButton(){
//        prev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentPage++;
//            }
//        });
//    }
//
//    private void setButtonSetting(){
//        // Reaching the end of the page
//        if(currentPage == totalPage){
//            prev.setEnabled(true);
//            next.setEnabled(false);
//        }
//        // Reaching the first page
//        else if(currentPage == 0){
//            prev.setEnabled(false);
//            next.setEnabled(true);
//        }
//        // Reaching currentPage > 1 & < end
//        else{
//            prev.setEnabled(true);
//            next.setEnabled(true);
//        }
//    }
}
