package com.thesis.megahjaya.Penjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PenjualanInfoActivity extends AppCompatActivity {

    // For handling the data for spinner
    ArrayAdapter<CharSequence> arrayAdapter;

    // For parcelable
    private ArrayList<ListMaterialPenjualan> arrayListMaterialPenjualan;
    private ArrayList<Penjualan> penjualanArrayList;

    String getTipeBon; // Get tipe bon value
    Date currentTime;

    // url
    private static final String url = "";

    private Spinner tipeBon;
    private EditText invoiceNum, customerName, address, info;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_info);

        tipeBon = (Spinner) findViewById(R.id.spinnerPenjualanInfo);
        invoiceNum = (EditText) findViewById(R.id.penjualanInfoInvoiceNumber);
        customerName = (EditText) findViewById(R.id.penjualanInfoCustomerName);
        address = (EditText) findViewById(R.id.penjualanInfoAddress);
        info = (EditText) findViewById(R.id.penjualanInfoAdditionalInformation);
        nextBtn = (Button) findViewById(R.id.penjualanInfoNextBtn);

        // Retrieve data from parcel
        retrieveTempParcel();

        // Dropdown (spinner)
        dropdownSpinner();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalIntent = new Intent(PenjualanInfoActivity.this, PenjualanSuccessActivity.class);
                finalIntent.putParcelableArrayListExtra("listMaterial", penjualanArrayList);
                startActivity(finalIntent);
            }
        });
    }

    // Retrieve data from parcel
    public void retrieveTempParcel(){
        // Retrieve parcel data
        penjualanArrayList = getIntent().getParcelableArrayListExtra("listMaterial");
        Log.i("ARRAY_LIST_PARCELABLE", String.valueOf(arrayListMaterialPenjualan));

//        for(int x = 0; x< arrayListMaterialPenjualan.size(); x++){
//            arrayListMaterialPenjualan.addAll(x);
//        }

//        // Retrieve list of data from ArrayList
//        for(int x = 0; x < arrayListMaterialPenjualan.size(); x++){
//            ListMaterialPenjualan listMaterialPenjualan = arrayListMaterialPenjualan.get(x);
//
//            Log.i("GET_NAME", String.valueOf(listMaterialPenjualan.getName()));
//            Log.i("TOTAL_DATA", String.valueOf(arrayListMaterialPenjualan.size()));
//        }
    }

    // Dropdown (spinner)
    public void dropdownSpinner(){
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.listBon, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipeBon.setAdapter(arrayAdapter);
        tipeBon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTipeBon = String.valueOf(parent.getItemAtPosition(position));
//                Toast.makeText(PenjualanInfoActivity.this, String.valueOf(getTipeBon), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getInfoPenjualanData(ArrayList<Penjualan> penjualanArrayList){
        currentTime = Calendar.getInstance().getTime();

        HashMap<String, String> penjualanMap = new HashMap<>();
        penjualanMap.put("inv_number", invoiceNum.getText().toString());
        penjualanMap.put("inv_address", address.getText().toString());
//        penjualanMap.put("inv_totalPrice", );
        penjualanMap.put("inv_type", getTipeBon);

        JSONObject jsonObject = new JSONObject(penjualanMap);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
