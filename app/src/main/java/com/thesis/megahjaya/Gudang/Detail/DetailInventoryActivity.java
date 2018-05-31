package com.thesis.megahjaya.Gudang.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.megahjaya.Gudang.Update.UpdateActivity;
import com.thesis.megahjaya.R;

import org.w3c.dom.Text;

import java.util.HashMap;

public class DetailInventoryActivity extends AppCompatActivity {

    private final int GET_DATA = 0;

    private Toolbar toolbar;
    private Button ubah;
    private TextView namaBarang, kodeBarang, measurementBarang, grupBarang, jumlahBarang, stokMinimumBarang, hargaBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inventory);

        toolbar = (Toolbar) findViewById(R.id.detailToolbar);
        namaBarang = (TextView) findViewById(R.id.detailMaterialName);
        kodeBarang = (TextView) findViewById(R.id.detailMaterialCode);
        measurementBarang = (TextView) findViewById(R.id.detailMaterialUnit);
        grupBarang = (TextView) findViewById(R.id.detailMaterialGroup);
        jumlahBarang = (TextView) findViewById(R.id.detailMaterialQuantity);
        stokMinimumBarang = (TextView) findViewById(R.id.detailMaterialMinimumStock);
        hargaBarang = (TextView) findViewById(R.id.detailMaterialPrice);
        ubah = (Button) findViewById(R.id.editButton);

        // Set toolbar, title name & back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("detailMaterialName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDetailData();

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(DetailInventoryActivity.this, UpdateActivity.class);
                updateIntent.putExtra("detailMaterialName", namaBarang.getText().toString());
                updateIntent.putExtra("detailMaterialCode", kodeBarang.getText().toString());
                updateIntent.putExtra("detailMaterialMeasurement", measurementBarang.getText().toString());
                updateIntent.putExtra("detailMaterialGroup", grupBarang.getText().toString());
                updateIntent.putExtra("detailMaterialQuantity", jumlahBarang.getText().toString());
                updateIntent.putExtra("detailMaterialMinimum", stokMinimumBarang.getText().toString());
                updateIntent.putExtra("detailMaterialPrice", hargaBarang.getText().toString());
                startActivityForResult(updateIntent, GET_DATA);
            }
        });
    }

    private void getDetailData(){
        namaBarang.setText(getIntent().getStringExtra("detailMaterialName"));
        kodeBarang.setText(getIntent().getStringExtra("detailMaterialCode"));
        measurementBarang.setText(getIntent().getStringExtra("detailMaterialMeasurement"));
        grupBarang.setText(getIntent().getStringExtra("detailMaterialGroup"));
        jumlahBarang.setText(getIntent().getStringExtra("detailMaterialQuantity"));
        stokMinimumBarang.setText(getIntent().getStringExtra("detailMaterialMinimum"));
        hargaBarang.setText(getIntent().getStringExtra("detailMaterialPrice"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_DATA && resultCode == RESULT_OK){
            Bundle getDetailData = data.getExtras();

            HashMap<String, String> hashMap = (HashMap) getDetailData.get("updateResult");
            namaBarang.setText(hashMap.get("name"));
            kodeBarang.setText(hashMap.get("itemCode"));
            measurementBarang.setText(hashMap.get("measurement"));
            grupBarang.setText(hashMap.get("group"));
            jumlahBarang.setText(hashMap.get("quantity"));
            stokMinimumBarang.setText(hashMap.get("minimum"));
            hargaBarang.setText(hashMap.get("price"));
        }
    }
}