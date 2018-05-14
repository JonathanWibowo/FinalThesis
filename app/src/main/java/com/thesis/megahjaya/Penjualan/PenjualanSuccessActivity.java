package com.thesis.megahjaya.Penjualan;

import android.content.Intent;
import android.service.autofill.FillEventHistory;
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

import java.util.ArrayList;

public class PenjualanSuccessActivity extends AppCompatActivity {

    // For parcelable
    private Penjualan penjualan;
    private ArrayList<Penjualan> penjualanArrayList;

    // For recycler view
    private RecyclerView recyclerView;
    private PenjualanSuccessAdapter penjualanSuccessAdapter;

    // For list item (view data)
    private ArrayList<ListMaterialPenjualanSuccess> listMaterialPenjualanSuccessArrayList;
    private ListMaterialPenjualanSuccess listMaterialPenjualanSuccess;

    private Integer totalWholeMaterial = 0;

    private TextView textView;
    private Button cekBon, menuUtama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_success);

        textView = (TextView) findViewById(R.id.textView);
        cekBon = (Button) findViewById(R.id.penjualanSuccessCheckInvoiceBtn);
        menuUtama = (Button) findViewById(R.id.penjualanSuccessMainMenuBtn);

        recyclerView = (RecyclerView) findViewById(R.id.penjualanSuccessRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSuccess = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManagerSuccess);
        listMaterialPenjualanSuccessArrayList = new ArrayList<>();

        // Retrieve data from parcel
        retrieveParcel();

        cekBon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenjualanSuccessActivity.this, HistoriPenjualanActivity.class));
            }
        });

        menuUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenjualanSuccessActivity.this, PenjualanActivity.class));
            }
        });
    }

    // Retrieve data from parcel
    private void retrieveParcel(){
        // Retrieve parcel data
        penjualanArrayList = getIntent().getParcelableArrayListExtra("listMaterial");
        Log.i("ARRAY_LIST_SUCCESS", String.valueOf(penjualanArrayList));

        // Retrieve list of data from ArrayList
        for(int x = 0; x < penjualanArrayList.size(); x++){
            penjualan = penjualanArrayList.get(x);

            totalWholeMaterial = totalWholeMaterial + penjualan.getTotalUnitPrice();

            listMaterialPenjualanSuccess = new ListMaterialPenjualanSuccess(
                    penjualan.getName(),
                    penjualan.getQuantity(),
                    penjualan.getUnitPrice(),
                    penjualan.getTotalUnitPrice()
            );

            listMaterialPenjualanSuccessArrayList.add(listMaterialPenjualanSuccess);
        }

        penjualanSuccessAdapter = new PenjualanSuccessAdapter(listMaterialPenjualanSuccessArrayList, getApplicationContext());
        recyclerView.setAdapter(penjualanSuccessAdapter);

//        textView.setText(penjualan.getName() + ", " + penjualan.getQuantity() + ", " + penjualan.getUnitPrice() + ", " + penjualan.getTotalUnitPrice() + "\n");
        Log.i("GET_NAME", String.valueOf(penjualan.getName()));
        Log.i("TOTAL_DATA", String.valueOf(penjualanArrayList.size()));
        Log.i("listmaterialpenjualan", String.valueOf(listMaterialPenjualanSuccessArrayList));
        Log.i("listsize", String.valueOf(listMaterialPenjualanSuccessArrayList.size()));
        Log.i("totalPrice", String.valueOf(totalWholeMaterial));
    }
}
