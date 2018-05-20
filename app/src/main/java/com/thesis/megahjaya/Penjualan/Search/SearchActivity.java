package com.thesis.megahjaya.Penjualan.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.MaterialInventory;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.Penjualan.Search.SearchAdapterFile.SearchAdapter;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    // For recyclerview
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    // For list material
    private ArrayList<SearchMaterial> searchMaterialArrayList;
    private SearchMaterial searchMaterial;

    private static final String url = "https://thesisandroid.000webhostapp.com/material/listMaterial.php";

    private EditText editText;
    private CheckBox checkBox;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = (EditText) findViewById(R.id.editTextSearchMaterialName);
        checkBox = (CheckBox) findViewById(R.id.searchCheckBox);
        okButton = (Button) findViewById(R.id.okButton);

        // manage recycler view
        manageRecyclerView();

        // search function
        search();

        // display list of material
        displayMaterial();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(SearchActivity.this, PenjualanActivity.class);
//                searchIntent.putExtra("materialName", );
                startActivity(searchIntent);
            }
        });
    }

    private void manageRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchMaterialArrayList = new ArrayList<>();
    }

    private void search(){
        editText.addTextChangedListener(textWatcher);




//        searchMaterial.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String string) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String string) {
//                ArrayList<MaterialInventory> getListInventoryName = new ArrayList<>();
//
//                for(MaterialInventory listInventory : listMaterialInventories){
//                    String getMaterialName = listInventory.getName().toLowerCase();
//
//                    if(getMaterialName.contains(string)){
//                        getListInventoryName.add(listInventory);
//                    }
//                }
//
//                inventoryAdapter.setFilter(getListInventoryName);
//
//                return true;
//            }
//        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchAdapter.setFilter(searchMaterialArrayList);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editText.removeTextChangedListener(textWatcher);
    }

    private void displayMaterial(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getStatusCode = response.getInt("status");

                            if(getStatusCode == 200){
                                JSONArray jsonArray = response.getJSONArray("material");

                                for(int x = 0; x < jsonArray.length(); x++){
                                    JSONObject insideJsonArray = jsonArray.getJSONObject(x);

                                    searchMaterial = new SearchMaterial(
                                            insideJsonArray.getString("name"),
                                            insideJsonArray.getInt("quantity"),
                                            insideJsonArray.getInt("price")
                                    );
                                    searchMaterialArrayList.add(searchMaterial);
                                }

                                // Set the adapter
                                searchAdapter = new SearchAdapter(searchMaterialArrayList, SearchActivity.this);
                                recyclerView.setAdapter(searchAdapter);
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
