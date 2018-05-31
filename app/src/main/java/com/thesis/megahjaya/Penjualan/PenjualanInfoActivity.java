package com.thesis.megahjaya.Penjualan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Gudang.AddNewMaterial.AddNewMaterialActivity;
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
import java.util.Map;

public class PenjualanInfoActivity extends AppCompatActivity {

    // For parcelable
    private ArrayList<PenjualanTemp> penjualanTempArrayList;
    private PenjualanTemp penjualanTemp;

    // For new place to put the data from previous activity
    private ArrayList<MaterialPenjualanSuccess> materialPenjualanSuccessArrayList;
    private MaterialPenjualanSuccess materialPenjualanSuccess;

    // For handling the data for spinner
    ArrayAdapter<CharSequence> tipeBonArrayAdapter;
    ArrayAdapter<CharSequence> statusBonArrayAdapter;

    // For calculate whole material
    private Integer totalUnitPrice = 0;
    private Integer totalWholeMaterialPrice = 0;

    // Value for tipe bon
    private String getTipeBon;
    private Integer setTipeBon;

    // Value for status bon
    private String getStatusBon;
    private Integer setStatusBon;

    // For calendar
    private String currDate;
    private Calendar calendar;

    // url
    private static final String url = "https://thesisandroid.000webhostapp.com/invoice/makeInvoice.php";


    private Toolbar toolbar;
    private Spinner tipeBon;
    private EditText invoiceNum, name, address, telpNumber, info, discount;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_info);

        toolbar = (Toolbar) findViewById(R.id.penjualanInfoToolbar);
        tipeBon = (Spinner) findViewById(R.id.spinnerPenjualanInfo);
        invoiceNum = (EditText) findViewById(R.id.penjualanInfoInvoiceNumber);
        name = (EditText) findViewById(R.id.penjualanInfoCustomerName);
        address = (EditText) findViewById(R.id.penjualanInfoAddress);
        telpNumber = (EditText) findViewById(R.id.penjualanInfoTelephone);
        info = (EditText) findViewById(R.id.penjualanInfoAdditionalInformation);
        discount = (EditText) findViewById(R.id.penjualanInfoDiscount);
        nextBtn = (Button) findViewById(R.id.penjualanInfoNextBtn);

        // set toolbar name & back button
        setToolbar();

        // Retrieve data from parcel
        retrieveTempParcel();

        // Tipe Bon
        tipeBonSpinner();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the name and address is still blank
                if(name.getText().toString().isEmpty() || address.getText().toString().isEmpty()){
                    Toast.makeText(PenjualanInfoActivity.this, "Nama pembeli & alamat wajib diisi", Toast.LENGTH_LONG).show();
                }
                else{
                // input data to database
                    getInfoPenjualanData(materialPenjualanSuccessArrayList, setTipeBon, setStatusBon);
                }
            }
        });
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Penjualan Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        return materialPenjualanSuccessArrayList;
    }

    // Dropdown for invoice type
    private Integer tipeBonSpinner(){
        tipeBonArrayAdapter = ArrayAdapter.createFromResource(this, R.array.listBon, android.R.layout.simple_spinner_item);
        tipeBonArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipeBon.setAdapter(tipeBonArrayAdapter);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return setTipeBon;
    }

    private void getInfoPenjualanData(ArrayList<MaterialPenjualanSuccess> materialPenjualanSuccessArrayList, final Integer setTipeBon, Integer setStatusBon){
        // get the current time
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currDate = simpleDateFormat.format(calendar.getTime());

        // get total whole price
        getTotalPrice();

        // put list of data to JSON Array
        JSONArray jsonArray = new JSONArray();

        for(int x = 0; x < materialPenjualanSuccessArrayList.size(); x++){
            // get list data from previous activity and insert it to JSONObject
            JSONObject getListMaterial = new JSONObject();
            try {
                getListMaterial.put("name", materialPenjualanSuccessArrayList.get(x).getName());
                getListMaterial.put("quantity", materialPenjualanSuccessArrayList.get(x).getBuyQuantity());
                getListMaterial.put("unitPrice", materialPenjualanSuccessArrayList.get(x).getUnitPrice());
                getListMaterial.put("totalPrice", materialPenjualanSuccessArrayList.get(x).getTotalPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // after finish put list of data to JSONObject, insert the object into JSONArray
            jsonArray.put(getListMaterial);
        }

        // put list array to json object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("inv_type", setTipeBon.toString());
            jsonObject.put("inv_date", currDate.toString());
            jsonObject.put("inv_number", invoiceNum.getText().toString());
            jsonObject.put("inv_name", name.getText());
            jsonObject.put("inv_address", address.getText().toString());
//            jsonObject.put("cst_contact", telpNumber.getText().toString());
            jsonObject.put("inv_info", info.getText().toString());
            jsonObject.put("inv_list_material", jsonArray);
            jsonObject.put("inv_discount", discount.getText().toString());
            jsonObject.put("inv_total_price", totalWholeMaterialPrice.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getResponseCode = response.getInt("status");

                            if(getResponseCode == 201){
                                // Pass some important data to be displayed to next page
                                Intent finalIntent = new Intent(PenjualanInfoActivity.this, PenjualanSuccessActivity.class);
                                finalIntent.putParcelableArrayListExtra("listMaterial", penjualanTempArrayList);
                                finalIntent.putExtra("invoiceType", setTipeBon.toString());
                                finalIntent.putExtra("invoiceDate", currDate);
                                finalIntent.putExtra("customerName", name.getText().toString());
                                finalIntent.putExtra("customerAddress", address.getText().toString());
                                finalIntent.putExtra("customerInfo", info.getText().toString());
                                finalIntent.putExtra("customerTotalPrice", totalWholeMaterialPrice);
                                startActivity(finalIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null && error.networkResponse.statusCode == 400){
//                    Toast.makeText(PenjualanInfoActivity.this, "ERROR! Silahkan periksa data kembali sebelum melanjutkan ke halaman selanjutnya", Toast.LENGTH_LONG).show();
                    dataErrorMessage();
                }
                if(error.networkResponse != null && error.networkResponse.statusCode == 500){
                    serverErrorMessage();
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void dataErrorMessage(){
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PenjualanInfoActivity.this);
        alertDialogBuilder.setMessage("Mohon cek kembali data sebelum lanjut ke tahap terakhir")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        // Create alert dialog message
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Error");
        alertDialog.show();
    }

    private void serverErrorMessage(){
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PenjualanInfoActivity.this);
        alertDialogBuilder.setMessage("Terjadi masalah pada server")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        // Create alert dialog message
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Server Error");
        alertDialog.show();
    }

    private int getTotalPrice(){
        // get total whole price
        for(int x = 0; x < materialPenjualanSuccessArrayList.size(); x++){
            materialPenjualanSuccess = materialPenjualanSuccessArrayList.get(x);

            totalWholeMaterialPrice = totalWholeMaterialPrice + materialPenjualanSuccess.getTotalPrice();
        }

        return totalWholeMaterialPrice;
    }
}
