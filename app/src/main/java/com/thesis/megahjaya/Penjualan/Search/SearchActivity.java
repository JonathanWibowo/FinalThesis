package com.thesis.megahjaya.Penjualan.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.Penjualan.Search.SearchAdapter.SearchAdapter;
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

    // Array list for checkbox
    private ArrayList<SearchMaterial> selectionMaterialArrayList = new ArrayList<>();
    int count = 0;

    private static final String url = "https://thesisandroid.000webhostapp.com/material/listMaterial.php";

    private SearchView searchView;
    private CheckBox checkBox;
    private Button okButton;

    StringBuffer stringBuffer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchViewMaterialName);
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
//                stringBuffer = new StringBuffer();

//                for(SearchMaterial searchMaterial : searchAdapter){
//
//                }

                Intent searchIntent = new Intent(SearchActivity.this, PenjualanActivity.class);
//                searchIntent.putExtra("materialName", );
                startActivity(searchIntent);

            }
        });
    }

    private void manageRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchMaterialArrayList = new ArrayList<>();

        searchAdapter = new SearchAdapter(searchMaterialArrayList, this);

    }

    private void search(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                ArrayList<SearchMaterial> getSearchMaterialArrayList = new ArrayList<>();

                for(SearchMaterial searchMaterial : searchMaterialArrayList){
                    String getMaterialName = searchMaterial.getMaterialName().toLowerCase();

                    if(getMaterialName.contains(string)){
                        getSearchMaterialArrayList.add(searchMaterial);
                    }
                }

                searchAdapter.setFilter(getSearchMaterialArrayList);

                return true;
            }
        });
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

    public void selectMaterial(View view, int position){
        // Check if the checkbox is checked or not
        if(((CheckBox)view).isChecked()){
            selectionMaterialArrayList.add(searchMaterialArrayList.get(position));

            Toast.makeText(SearchActivity.this, searchMaterialArrayList.get(position).getMaterialName(), Toast.LENGTH_LONG).show();
        }
        else{
            selectionMaterialArrayList.remove(searchMaterialArrayList.get(position));
        }
    }

    public void retrieveMaterialCode() {
        // Get scanned value and bring the scanned value to previous activity
        Intent intent = new Intent(SearchActivity.this, PenjualanActivity.class);
//        intent.putExtra("materialCode", String.valueOf(result));
        setResult(RESULT_OK, intent);
        finish();
    }
}
