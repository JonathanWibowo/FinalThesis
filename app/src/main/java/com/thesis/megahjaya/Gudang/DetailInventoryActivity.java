package com.thesis.megahjaya.Gudang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.megahjaya.R;

import org.w3c.dom.Text;

public class DetailInventoryActivity extends AppCompatActivity {

    private Button ubah;
    private TextView namaBarang, kodeBarang, deskripsiBarang, grupBarang, jumlahBarang, hargaBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inventory);

        ubah = (Button) findViewById(R.id.editButton);
        namaBarang = (TextView) findViewById(R.id.detailMaterialName);
        kodeBarang = (TextView) findViewById(R.id.detailMaterialCode);
        deskripsiBarang = (TextView) findViewById(R.id.detailMaterialDescription);
        grupBarang = (TextView) findViewById(R.id.detailMaterialGroup);
        jumlahBarang = (TextView) findViewById(R.id.detailMaterialQuantity);
        hargaBarang = (TextView) findViewById(R.id.detailMaterialPrice);

//        getSupportActionBar().setTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the data
        namaBarang.setText(getIntent().getStringExtra("detailMaterialName"));
        kodeBarang.setText(getIntent().getStringExtra("detailMaterialCode"));
        deskripsiBarang.setText(getIntent().getStringExtra("detailMaterialDescription"));
        grupBarang.setText(getIntent().getStringExtra("detailMaterialGroup"));
        jumlahBarang.setText(getIntent().getStringExtra("detailMaterialQuantity"));
//        jumlahBarang.setText(getIntent().getIntExtra("detailMaterialQuantity", 0));
        hargaBarang.setText(getIntent().getStringExtra("detailMaterialPrice"));
    }
}