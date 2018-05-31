package com.thesis.megahjaya.Penjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.megahjaya.Histori_Penjualan.HistoriPenjualanActivity;
import com.thesis.megahjaya.Penjualan.Adapter.PenjualanSuccessAdapter;
import com.thesis.megahjaya.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PenjualanSuccessActivity extends AppCompatActivity {

    // For parcelable
    private ArrayList<PenjualanTemp> penjualanTempArrayList;
    private PenjualanTemp penjualanTemp;

    // For recycler view
    private RecyclerView recyclerView;
    private PenjualanSuccessAdapter penjualanSuccessAdapter;

    // For list item (view data)
    private ArrayList<MaterialPenjualanSuccess> listMaterialPenjualanSuccessArrayList;
    private MaterialPenjualanSuccess materialPenjualanSuccess;

    // For calculate whole material
    private Integer totalWholeMaterial = 0;

    private TextView invoiceDate, customerName, customerAddress, customerInfo, totalWholePrice;
    private Button cekBon, menuUtama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_success);

        invoiceDate = (TextView) findViewById(R.id.pembelianSuccessCurrentDate);
        customerName = (TextView) findViewById(R.id.pembelianSuccessCustomerName);
        customerAddress = (TextView) findViewById(R.id.pembelianSuccessCustomerAddress);
        customerInfo = (TextView) findViewById(R.id.pembelianSuccessCustomerInformation);
        totalWholePrice = (TextView) findViewById(R.id.pembelianSuccessTotalPrice);
        cekBon = (Button) findViewById(R.id.penjualanSuccessCheckInvoiceBtn);
        menuUtama = (Button) findViewById(R.id.penjualanSuccessMainMenuBtn);

        // Manage recycler view
        manageRecyclerView();

        // Retrieve data from parcel
        retrieveAllData();

//        cekBon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PenjualanSuccessActivity.this, HistoriPenjualanActivity.class));
//            }
//        });

        menuUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenjualanSuccessActivity.this, PenjualanActivity.class));
            }
        });
    }

    private void manageRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.penjualanSuccessRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSuccess = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManagerSuccess);
        listMaterialPenjualanSuccessArrayList = new ArrayList<>();
    }

    // Retrieve data from parcel
    private void retrieveAllData(){
        // Retrieve parcel data
        penjualanTempArrayList = getIntent().getParcelableArrayListExtra("listMaterial");

        // Retrieve list of data from ArrayList
        for(int i = 0; i < penjualanTempArrayList.size(); i++){
            penjualanTemp = penjualanTempArrayList.get(i);

            // Calculate whole price
            totalWholeMaterial = totalWholeMaterial + penjualanTemp.getTotalUnitPrice();

            // Insert data to new array list
            materialPenjualanSuccess = new MaterialPenjualanSuccess(
                    penjualanTemp.getName(),
                    penjualanTemp.getQuantity(),
                    penjualanTemp.getUnitPrice(),
                    penjualanTemp.getTotalUnitPrice()
            );

            listMaterialPenjualanSuccessArrayList.add(materialPenjualanSuccess);
        }

        penjualanSuccessAdapter = new PenjualanSuccessAdapter(listMaterialPenjualanSuccessArrayList, PenjualanSuccessActivity.this);
        recyclerView.setAdapter(penjualanSuccessAdapter);

        // set customer name, invoice date & total price
        invoiceDate.setText(getIntent().getStringExtra("invoiceTime"));
        customerName.setText(getIntent().getStringExtra("customerName") + " (Bon " + getIntent().getStringExtra("invoiceType") + ")");
        customerAddress.setText(getIntent().getStringExtra("customerAddress"));
        customerInfo.setText(getIntent().getStringExtra("customerInfo"));
        totalWholePrice.setText("Rp " + getIntent().getStringExtra("customerTotalPrice"));

//        Log.i("GET_NAME", String.valueOf(penjualanTemp.getName()));
//        Log.i("TOTAL_DATA", String.valueOf(penjualanTempArrayList.size()));
//        Log.i("listmaterialpenjualan", String.valueOf(listMaterialPenjualanSuccessArrayList));
//        Log.i("listsize", String.valueOf(listMaterialPenjualanSuccessArrayList.size()));
//        Log.i("totalPrice", String.valueOf(totalWholeMaterial));
    }
}
