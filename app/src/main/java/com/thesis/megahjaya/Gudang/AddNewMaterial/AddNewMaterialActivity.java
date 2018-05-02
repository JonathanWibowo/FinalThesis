package com.thesis.megahjaya.Gudang.AddNewMaterial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONObject;

import java.util.HashMap;

public class AddNewMaterialActivity extends AppCompatActivity {

    private EditText newName, newCode, newDesc, newGroup, newQuantity, newPrice;
    private Button addNew;

    final String url = "http://192.168.1.5/thesis_test/listMaterial/newItem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_material);

        getSupportActionBar().setTitle("Tambah Barang Baru"); // Give title to current navigation bar

        newName = (EditText) findViewById(R.id.addNewMaterialName);
        newCode = (EditText) findViewById(R.id.addNewMaterialCode);
        newDesc = (EditText) findViewById(R.id.addNewMaterialDescription);
        newGroup = (EditText) findViewById(R.id.addNewMaterialGroup);
        newQuantity = (EditText) findViewById(R.id.addNewMaterialQuantity);
        newPrice = (EditText) findViewById(R.id.addNewMaterialPrice);
        addNew = (Button) findViewById(R.id.addNewMaterialButton);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, code, desc, group;
                int quantity, price;

                name = newName.getText().toString();
                code = newCode.getText().toString();
                desc = newDesc.getText().toString();
                group = newGroup.getText().toString();
                quantity = Integer.parseInt(newQuantity.getText().toString());
                price = Integer.parseInt(newPrice.getText().toString());

                // Get data from user input
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", name);
                hashMap.put("itemCode", code);
                hashMap.put("desc", desc);
//                hashMap.put("group", group);
//                hashMap.put("quantity", quantity);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

//                addNewMaterial();
            }
        });
    }

//    private void addNewMaterial(){
//
//    }
}
