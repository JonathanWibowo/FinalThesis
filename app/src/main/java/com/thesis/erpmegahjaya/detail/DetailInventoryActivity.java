package com.thesis.erpmegahjaya.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.erpmegahjaya.GudangActivity;
import com.thesis.erpmegahjaya.R;

public class DetailInventoryActivity extends AppCompatActivity {

    private Button ubah;
    private TextView namaBarang, kodeBarang, deskripsiBarang, stokBarang, hargaSatuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inventory);

        namaBarang = (TextView) findViewById(R.id.detailMaterialName);
        kodeBarang = (TextView) findViewById(R.id.detailMaterialCode);
        deskripsiBarang = (TextView) findViewById(R.id.detailMaterialDescription);
        stokBarang = (TextView) findViewById(R.id.detailMaterialQuantity);
        hargaSatuan = (TextView) findViewById(R.id.detailMaterialPrice);
        ubah = (Button) findViewById(R.id.editButton);

        // Get the data
        namaBarang.setText(getIntent().getStringExtra("detailNamaBarang"));
        kodeBarang.setText(getIntent().getStringExtra("detailKodeBarang"));
        deskripsiBarang.setText(getIntent().getStringExtra("detailDeskripsi"));
        stokBarang.setText(getIntent().getStringExtra("detailStokBarang"));
        hargaSatuan.setText(getIntent().getStringExtra("detailHargaBarang"));
    }
}
