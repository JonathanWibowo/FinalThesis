package com.thesis.megahjaya.Penjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.R;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PenjualanInfoActivity extends AppCompatActivity {

    // For parcelable
    private ArrayList<PenjualanTemp> penjualanTempArrayList;
    private PenjualanTemp penjualanTemp;

    // For new place to put the data from previous activity
    private ArrayList<MaterialPenjualanSuccess> materialPenjualanSuccessArrayList;
    private MaterialPenjualanSuccess materialPenjualanSuccess;

    // For handling the data for spinner
    ArrayAdapter<CharSequence> arrayAdapter;

    // For calculate whole material
    private Integer totalUnitPrice = 0;
    private Integer totalWholeMaterialPrice = 0;

    String getTipeBon; // Get tipe bon value
    Integer setTipeBon;
    String currDate;
    private Calendar calendar;
//    Date currentDate;

    // url
    private static final String url = "";

    private Toolbar toolbar;
    private Spinner tipeBon;
    private EditText invoiceNum, customerName, address, info;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_info);

        toolbar = (Toolbar) findViewById(R.id.penjualanInfoToolbar);
        tipeBon = (Spinner) findViewById(R.id.spinnerPenjualanInfo);
        invoiceNum = (EditText) findViewById(R.id.penjualanInfoInvoiceNumber);
        customerName = (EditText) findViewById(R.id.penjualanInfoCustomerName);
        address = (EditText) findViewById(R.id.penjualanInfoAddress);
        info = (EditText) findViewById(R.id.penjualanInfoAdditionalInformation);
        nextBtn = (Button) findViewById(R.id.penjualanInfoNextBtn);

        // set toolbar name & back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Penjualan Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve data from parcel
        retrieveTempParcel();

        Log.i("returnStatement", String.valueOf(retrieveTempParcel()));

        // Dropdown (spinner)
        dropdownSpinner();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getInfoPenjualanData(materialPenjualanSuccessArrayList);
                calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                currDate = simpleDateFormat.format(calendar.getTime());

                // get total whole price
                for(int x = 0; x < materialPenjualanSuccessArrayList.size(); x++){
                    materialPenjualanSuccess = materialPenjualanSuccessArrayList.get(x);

                    totalWholeMaterialPrice = totalWholeMaterialPrice + materialPenjualanSuccess.getTotalPrice();
                }

//                Log.i("totalPrice", String.valueOf(totalWholeMaterialPrice));

                Intent finalIntent = new Intent(PenjualanInfoActivity.this, PenjualanSuccessActivity.class);
                finalIntent.putParcelableArrayListExtra("listMaterial", penjualanTempArrayList);
                finalIntent.putExtra("invoiceTime", currDate);
                finalIntent.putExtra("customerName", customerName.getText().toString());
                finalIntent.putExtra("totalWholeMaterialPrice", totalWholeMaterialPrice);
                startActivity(finalIntent);
            }
        });
    }

    // Retrieve data from parcel
    private ArrayList<MaterialPenjualanSuccess> retrieveTempParcel(){
        // set the array list
        materialPenjualanSuccessArrayList = new ArrayList<>();

        // Retrieve parcel data
        penjualanTempArrayList = getIntent().getParcelableArrayListExtra("listMaterial");

        for(int x = 0; x< penjualanTempArrayList.size(); x++){
            penjualanTemp = penjualanTempArrayList.get(x);

            // Calculate the total unit price
            totalUnitPrice = penjualanTemp.getQuantity() * penjualanTemp.getUnitPrice();

            // Insert data to new array list
            materialPenjualanSuccess = new MaterialPenjualanSuccess(
                penjualanTemp.getName(),
                penjualanTemp.getQuantity(),
                penjualanTemp.getUnitPrice(),
                totalUnitPrice
            );

            materialPenjualanSuccessArrayList.add(materialPenjualanSuccess);
        }

        Log.i("totalUnitPrice", String.valueOf(totalUnitPrice));

        return materialPenjualanSuccessArrayList;
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

                // Check the value set by user
                if(getTipeBon.equals("Bon 1")){
                    setTipeBon = 1;
                }
                if(getTipeBon.equals("Bon 2")){
                    setTipeBon = 2;
                }
                if(getTipeBon.equals("Bon 3")){
                    setTipeBon = 3;
                }
//                Toast.makeText(PenjualanInfoActivity.this, getTipeBon, Toast.LENGTH_LONG).show();
//                Toast.makeText(PenjualanInfoActivity.this, String.valueOf(setTipeBon), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getInfoPenjualanData(ArrayList<MaterialPenjualanSuccess> materialPenjualanSuccessArrayList){
        // get the current time
//        currentDate = Calendar.getInstance().getTime();
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currDate = simpleDateFormat.format(calendar.getTime());

        // get total whole price
        for(int x = 0; x < materialPenjualanSuccessArrayList.size(); x++){
            materialPenjualanSuccess = materialPenjualanSuccessArrayList.get(x);

            totalWholeMaterialPrice = totalWholeMaterialPrice + materialPenjualanSuccess.getTotalPrice();
        }

        // make JSONArray & put array list data into JSONArray
        JSONArray jsonArray = new JSONArray(materialPenjualanSuccessArrayList);

        for(int y = 0; y < materialPenjualanSuccessArrayList.size(); y++){
            jsonArray.put(materialPenjualanSuccessArrayList.get(y));
        }

        // Get value from edit text
        HashMap<String, String> penjualanMap = new HashMap<>();
        penjualanMap.put("inv_number", invoiceNum.getText().toString());
        penjualanMap.put("inv_date", currDate.toString());
        penjualanMap.put("inv_address", address.getText().toString());
        penjualanMap.put("inv_totalPrice", String.valueOf(totalWholeMaterialPrice));
        penjualanMap.put("inv_type", getTipeBon.toString());

        JSONObject jsonObject = new JSONObject(penjualanMap);

        // Insert array list of materialchose by customer inside JSON Object
        try {
            jsonObject.put("list", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
